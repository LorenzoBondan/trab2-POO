package movieticket.services;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import movieticket.entities.Gender;

public class GenderService {

	private String file = "arquivo.csv";
	
	public List<Gender> findAll() {
		return load();
	}
	
	public Optional<Gender> findById(Long id) {
		List<Gender> list = load();
        return list.stream()
                .filter(gender -> gender.getId() == id)
                .findFirst();
	}
	
    public void insert(Gender gender) {
        List<Gender> list = load(); // carrega a lista de objetos já cadastrados no csv
        list.add(gender); // adiciona um novo objeto a essa lista
        save(list); // salva a lista de objetos no csv
    }
    
    public void update(Gender updatedGender) {
        List<Gender> list = load();
        for (Gender gender : list) {
            if (gender.getId() == updatedGender.getId()) {
            	gender.setName(updatedGender.getName());
                save(list);
                return;
            }
        }
    }

    public void delete(Long id) {
        List<Gender> list = load();
        list.removeIf(gender -> gender.getId() == id);
        save(list);
    }
	
    public void save(List<Gender> list) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (Gender gender : list) {
                writer.write(gender.getId() + "," + gender.getName());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
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
            e.printStackTrace();
        }
        return list;
    }
}
