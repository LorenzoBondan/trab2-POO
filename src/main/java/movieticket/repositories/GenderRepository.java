package movieticket.repositories;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import movieticket.entities.Gender;
import movieticket.exceptions.DuplicateResourceException;
import movieticket.exceptions.ResourceNotFoundException;

public class GenderRepository {

	private String file = "genres.csv";
	
	public List<Gender> findAll() {
		return load();
	}
	
	public Optional<Gender> findById(Long id) {
	    List<Gender> list = load();
	    return Optional.ofNullable(list.stream()
	            .filter(gender -> gender.getId().equals(id))
	            .findFirst()
	            .orElseThrow(() -> new ResourceNotFoundException("Gênero com Id " + id + " não encontrado.")));
	}
	
	public void insert(Gender gender) {
	    List<Gender> list = load(); // carrega a lista de objetos já cadastrados no csv
	    Long newId = gender.getId(); // id do objeto a ser inserido
	    boolean idExists = list.stream().anyMatch(existingGender -> existingGender.getId().equals(newId)); // percorre a lista para ver se o id já está cadastrado
	    if (idExists) {
	        throw new DuplicateResourceException("Gênero com ID " + newId + " já existe.");
	    }
	    list.add(gender); // adiciona o objeto a lista
	    save(list); // salva a lista novamente
	}

    public void update(Gender updatedGender) {
        List<Gender> list = load(); // carrega a lista de objetos já cadastrados no csv
        boolean isUpdated = false; // inicia como falso para encontrar o objeto

        for (Gender gender : list) { // percorre a lista
            if (gender.getId().equals(updatedGender.getId())) { // quando achou o objeto procurado, atualiza seus dados
                gender.setName(updatedGender.getName());
                isUpdated = true; // muda para verdadeiro
                break;
            }
        }

        if (!isUpdated) {
            throw new ResourceNotFoundException("Gênero com Id " + updatedGender.getId() + " não encontrado.");
        }
        save(list); // salva a lista novamente
    }

    public void delete(Long id) {
        List<Gender> list = load(); // carrega a lista de objetos já cadastrados no csv
        boolean isDeleted = list.removeIf(gender -> gender.getId().equals(id)); // verifica a existência do id e o deleta

        if (!isDeleted) {
            throw new ResourceNotFoundException("Gênero com Id " + id + " não encontrado.");
        }
        save(list);
    }

    public void save(List<Gender> list) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (Gender gender : list) {
                writer.write(gender.getId() + "," + gender.getName());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Falha ao abrir o arquivo: " + file);
        }
    }

    public List<Gender> load() {
        List<Gender> list = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(","); // atributos separados por vírgula
                Long id = Long.parseLong(data[0]);
                String name = data[1];
                Gender gender = new Gender(id, name);
                list.add(gender);
            }
        } catch (IOException e) {
            System.out.println("Falha ao abrir o arquivo: " + file);
        }
        return list;
    }
}
