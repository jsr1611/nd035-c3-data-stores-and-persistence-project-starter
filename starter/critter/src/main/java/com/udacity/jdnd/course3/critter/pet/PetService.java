package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.user.Customer;

import java.util.List;

public interface PetService {

    PetDTO save(PetDTO petDTO);

    PetDTO getPetDTOById(long petId);

    Pet getPetById(long petId);

    List<PetDTO> getAllPets();

    List<PetDTO> getPetsByOwner(long ownerId);

    Customer getOwnerByPet(long petId);
}
