package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.*;
import java.util.List;

@SpringBootApplication
public class DemoApplication {
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
}

@RestController
class FortuneController {

	@Autowired
	FortuneRepository repository;

	@RequestMapping("/random")
	public Fortune randomFortune() {
		List<Fortune> randomFortunes = repository.randomFortunes(new PageRequest(0,1));
		return randomFortunes.get(0);
	}
}

@RepositoryRestResource
interface FortuneRepository extends JpaRepository<Fortune, Long> {

	@org.springframework.data.jpa.repository.Query("select fortune from Fortune fortune order by RAND()")
	public List<Fortune> randomFortunes(Pageable pageable);
}

@Entity
@Table(name = "fortunes")
class Fortune {

	@Id
	@GeneratedValue
	private Long id;

	@Column
	private String text;

	public Fortune() {
	}

	@Override
	public String toString() {
		return "Fortune{" +
				"id=" + id +
				", text='" + text + '\'' +
				'}';
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}