package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.common.NotFoundException;
import com.udacity.jdnd.course3.critter.common.Utility;
import com.udacity.jdnd.course3.critter.user.Customer;
import com.udacity.jdnd.course3.critter.user.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class PetServiceImpl implements PetService{

    @Autowired
    private PetRepository petRepository;
    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public PetDTO save(PetDTO petDTO) {
        Pet pet = convertDTOToEntity(petDTO);
        pet = petRepository.save(pet);

        List<Pet> pets = pet.getOwner().getPets();
        if(pets == null){
            pets = new ArrayList<>();
            pets.add(pet);
        }else {
            pets.add(pet);
        }
        Customer customer = pet.getOwner();
        customer.setPets(pets);
        customerRepository.save(customer);
        return convertEntityToDTO(pet);
    }

    @Override
    public PetDTO getPetDTOById(long petId) {
        Optional<Pet> petOptional = petRepository.findById(petId);
        Utility.getInstance().throwExceptionIf(petOptional, "No pet was found for the given id: " + petId);
        return convertEntityToDTO(petOptional.get());
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
        Utility.getInstance().throwExceptionIf(pets, "No pets found for the given owner id: " + ownerId);
        return pets.stream().map(this::convertEntityToDTO).collect(Collectors.toList());
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
        Utility.getInstance().throwExceptionIf(optionalPet, "No customer found for the given pet id: " + petId);
        return optionalPet.get().getOwner();
    }
}
