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

public class PersonService {

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
                writer.write(person.getId() + "," + person.getName());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Person> load() {
        List<Person> list = new ArrayList<>();
        File fileObject = new File(file);

        // Verifica se o arquivo existe e tem tamanho maior que zero
        if (fileObject.exists() && fileObject.length() > 0) {
            try (BufferedReader reader = new BufferedReader(new FileReader(fileObject))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] data = line.split(","); // atributos separados por vírgula
                    Long id = Long.parseLong(data[0]);
                    String name = data[1];
                    if(!data[2].equals("null")) {
                    	Long marriedPersonId = Long.parseLong(data[2]);
                        Optional<Person> marriedPerson = findById(marriedPersonId);
                        if (marriedPerson.isPresent()) {
                            Person person = new Person(id, name, marriedPerson.get());
                            list.add(person);
                        } else {
                            // Trate o caso em que a pessoa casada não foi encontrada
                            // Pode lançar uma exceção ou lidar com isso de alguma outra forma
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("O arquivo está vazio ou não existe.");
            // Ou lance uma exceção ou trate de outra forma, conforme necessário
        }

        return list;
    }


}
