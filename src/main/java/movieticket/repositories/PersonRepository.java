package movieticket.repositories;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import movieticket.entities.Person;
import movieticket.exceptions.DuplicateResourceException;
import movieticket.exceptions.ResourceNotFoundException;

public class PersonRepository {

	private String file = "persons.csv";
	
	public List<Person> findAll() {
		return load();
	}
	
	public Optional<Person> findById(Long id) {
	    List<Person> list = load();
	    return Optional.ofNullable(list.stream()
	            .filter(person -> person.getId().equals(id))
	            .findFirst()
	            .orElseThrow(() -> new ResourceNotFoundException("Person with Id " + id + " not found.")));
	}
	
	public void insert(Person person) {
	    List<Person> list = load(); // carrega a lista de objetos já cadastrados no csv
	    Long newId = person.getId(); // id do objeto a ser inserido
	    boolean idExists = list.stream().anyMatch(existingPerson -> existingPerson.getId().equals(newId)); // percorre a lista para ver se o id já está cadastrado
	    if (idExists) {
	        throw new DuplicateResourceException("Person with ID " + newId + " already exists.");
	    }
	    list.add(person); // adiciona o objeto a lista
	    save(list); // salva a lista novamente
	}

    public void update(Person updatedPerson) {
        List<Person> list = load(); // carrega a lista de objetos já cadastrados no csv
        boolean isUpdated = false; // inicia como falso para encontrar o objeto

        for (Person person : list) { // percorre a lista
            if (person.getId().equals(updatedPerson.getId())) { // quando achou o objeto procurado, atualiza seus dados
                person.setName(updatedPerson.getName());
                person.setMarried(updatedPerson.getMarried());
                isUpdated = true; // muda para verdadeiro
                break;
            }
        }

        if (!isUpdated) {
            throw new ResourceNotFoundException("Person with Id " + updatedPerson.getId() + " not found.");
        }
        save(list); // salva a lista novamente
    }

    public void delete(Long id) {
        List<Person> list = load(); // carrega a lista de objetos já cadastrados no csv
        boolean isDeleted = list.removeIf(person -> person.getId().equals(id)); // verifica a existência do id e o deleta

        if (!isDeleted) {
            throw new ResourceNotFoundException("Person with Id " + id + " not found.");
        }
        save(list);
    }

    public void save(List<Person> list) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (Person person : list) {
                writer.write(person.getId() + "," 
                			+ person.getName() + "," 
                			+ person.getMarried().getId()
                );
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Person> load() {
        List<Person> list = new ArrayList<>();
        List<Long> marriedIds = new ArrayList<>(); // Lista para armazenar IDs das pessoas casadas

        File fileObject = new File(file);

        if (fileObject.exists() && fileObject.length() > 0) {
            try (BufferedReader reader = new BufferedReader(new FileReader(fileObject))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] data = line.split(","); // atributos separados por vírgula
                    Long id = Long.parseLong(data[0]);
                    String name = data[1];
                    Long marriedPersonId = Long.parseLong(data[2]);

                    if (marriedPersonId != 0) {
                        // Se o ID da pessoa casada não for 0, adicione-o à lista de IDs das pessoas casadas
                        marriedIds.add(marriedPersonId);
                    }

                    // Crie a pessoa sem associar a pessoa casada por enquanto
                    Person person = new Person(id, name, null);
                    list.add(person);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            // Associe as pessoas casadas na segunda passagem
            for (Person person : list) {
                Long marriedPersonId = marriedIds.get((int) ((Long) person.getId() - 1)); // -1 porque os IDs começam de 1
                if (marriedPersonId != 0) {
                    Person marriedPerson = list.get((int) (marriedPersonId - 1)); // -1 porque os IDs começam de 1
                    person.setMarried(marriedPerson);
                }
            }
        } else {
            System.out.println("Empty or non existing file.");
        }

        return list;
    }

}
