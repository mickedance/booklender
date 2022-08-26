package se.lexicon.micke.booklender.service;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.lexicon.micke.booklender.exception.ObjectNotFoundException;
import se.lexicon.micke.booklender.model.dto.LibraryUserDto;
import se.lexicon.micke.booklender.model.entity.LibraryUser;
import se.lexicon.micke.booklender.repository.LibraryUserRepository;

import java.util.List;

@Service
public class LibraryUserServiceImpl implements LibraryUserService {

    private LibraryUserRepository libraryUserRepository;
    private ModelMapper modelMapper;

    @Autowired
    public LibraryUserServiceImpl(LibraryUserRepository libraryUserRepository, ModelMapper modelMapper) {
        this.libraryUserRepository = libraryUserRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public LibraryUserDto findById(int id) throws ObjectNotFoundException {
        if (id < 0) throw new IllegalArgumentException("id must be 0 or more");
        LibraryUser user = libraryUserRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("user not found"));
        return modelMapper.map(user, LibraryUserDto.class);
    }

    @Override
    public LibraryUserDto findByEmail(String email) throws ObjectNotFoundException {
        if (email == null || email.equals("")) throw new IllegalArgumentException("email was null or empty");
        LibraryUser user = libraryUserRepository.findByEmail(email).orElseThrow(() -> new ObjectNotFoundException("user not found"));
        return modelMapper.map(user, LibraryUserDto.class);
    }

    @Override
    public List<LibraryUserDto> findAll() {
        return modelMapper.map(libraryUserRepository.findAll(),
                new TypeToken<List<LibraryUserDto>>() {
                }.getType());
    }

    @Override
    public LibraryUserDto create(LibraryUserDto libraryUserDto) {
        if (libraryUserDto == null) throw new IllegalArgumentException("libraryUserDto was null");
        LibraryUser userToSave = modelMapper.map(libraryUserDto, LibraryUser.class);
        LibraryUser savedUser = libraryUserRepository.save(userToSave);
        return modelMapper.map(savedUser, LibraryUserDto.class);
    }

    @Override
    public LibraryUserDto update(LibraryUserDto libraryUserDto) throws ObjectNotFoundException {
        if (libraryUserDto == null) throw new IllegalArgumentException("libraryUserDto was null");
        if (!libraryUserRepository.existsById(libraryUserDto.getId()))
            throw new ObjectNotFoundException("library User with email:" + libraryUserDto.getEmail() + " can not be found");
        LibraryUser userToBeSaved = modelMapper.map(libraryUserDto, LibraryUser.class);
        LibraryUser savedUser = libraryUserRepository.save(userToBeSaved);
        return modelMapper.map(savedUser, LibraryUserDto.class);
    }

    @Override
    public boolean delete(int id) throws ObjectNotFoundException {
        if (id < 0) throw new IllegalArgumentException("id must be 0 or more");
        if (!libraryUserRepository.existsById(id))
            throw new ObjectNotFoundException("library User that id was not be found");
        libraryUserRepository.deleteById(id);
        return true;
    }
}
