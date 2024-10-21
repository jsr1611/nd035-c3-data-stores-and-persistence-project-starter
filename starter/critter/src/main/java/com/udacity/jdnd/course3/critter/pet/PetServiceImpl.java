package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.common.NotFoundException;
import com.udacity.jdnd.course3.critter.user.Customer;
import com.udacity.jdnd.course3.critter.user.CustomerRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class PetServiceImpl implements PetService{

    private final PetRepository petRepository;
    private final CustomerRepository customerRepository;

    public PetServiceImpl(PetRepository petRepository, CustomerRepository customerRepository) {
        this.petRepository = petRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public PetDTO save(PetDTO petDTO) {
        Pet pet = convertDTOToEntity(petDTO);
        pet = petRepository.save(pet);
        return convertEntityToDTO(pet);
    }

    @Override
    public PetDTO getPetDTOById(long petId) {
        Optional<Pet> petOptional = petRepository.findById(petId);
        PetDTO dto = null;
        if(petOptional.isPresent()){
            dto = convertEntityToDTO(petOptional.get());
        }
        return dto;
    }

    @Override
    public Pet getPetById(long petId) {
        Optional<Pet> petOptional = petRepository.findById(petId);
        return petOptional.orElse(null);
    }

    @Override
    public List<PetDTO> getAllPets() {
        List<Pet> pets = petRepository.findAll();
        return pets.stream().map(this::convertEntityToDTO).collect(Collectors.toList());
    }

    @Override
    public List<PetDTO> getPetsByOwner(long ownerId) {
        List<Pet> pets = petRepository.findAllByOwner(ownerId);
        List<PetDTO> dtos = null;
        if(pets != null){
            dtos = pets.stream().map(this::convertEntityToDTO).collect(Collectors.toList());
        }
        return dtos;
    }

    private Pet convertDTOToEntity(PetDTO petDTO) {
        Pet pet = new Pet();
        pet.setId(petDTO.getId());
        pet.setName(petDTO.getName());
        pet.setBirthDate(petDTO.getBirthDate());
        pet.setType(petDTO.getType());
        Optional<Customer> optionalCustomer = customerRepository.findById(petDTO.getOwnerId());
        if(!optionalCustomer.isPresent()){
            throw new NotFoundException("Customer not found with id: " + petDTO.getOwnerId());
        }
        pet.setOwner(optionalCustomer.get());
        pet.setNotes(petDTO.getNotes());
        return pet;
    }

    private PetDTO convertEntityToDTO(Pet pet){
        PetDTO dto = new PetDTO();
        dto.setId(pet.getId());
        dto.setName(pet.getName());
        dto.setType(pet.getType());
        dto.setBirthDate(pet.getBirthDate());
        if(pet.getOwner() != null){
            dto.setOwnerId(pet.getOwner().getId());
        }
        dto.setNotes(pet.getNotes());
        return dto;
    }

    @Override
    public Customer getOwnerByPet(long petId) {
        Optional<Pet> optionalPet = petRepository.findById(petId);
        return optionalPet.map(Pet::getOwner).orElse(null);
    }
}