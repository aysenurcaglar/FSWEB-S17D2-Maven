package com.workintech.s17d2.rest;

import com.workintech.s17d2.model.*;
import com.workintech.s17d2.tax.DeveloperTax;
import com.workintech.s17d2.tax.Taxable;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/developers")
public class DeveloperController {
    private final Taxable taxable;
    public Map<Integer, Developer> developers;

    @Autowired
    public DeveloperController(DeveloperTax taxable) {
        this.taxable = taxable;
    }

    public Map<Integer, Developer> getDevelopers() {
        return developers;
    }

    @PostConstruct
    public void init() {
        developers = new HashMap<>();
    }


    @GetMapping
    public List<Developer> getAllDevelopers() {
        return new ArrayList<>(developers.values());
    }


    @GetMapping("/{id}")
    public Developer getDeveloperById(@PathVariable int id) {
        return developers.get(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Developer createDeveloper(@RequestBody Developer newDeveloper) {
        int id = newDeveloper.getId();
        double salary = newDeveloper.getSalary();
        Experience experience = newDeveloper.getExperience();

        Developer developer;

        switch (experience) {
            case JUNIOR:
                double simpleTax = salary * (taxable.getSimpleTaxRate() / 100);
                salary -= simpleTax;
                developer = new JuniorDeveloper(id, newDeveloper.getName(), salary);
                break;
            case MID:
                double middleTax = salary * (taxable.getMiddleTaxRate() / 100);
                salary -= middleTax;
                developer = new MidDeveloper(id, newDeveloper.getName(), salary);
                break;
            case SENIOR:
                double upperTax = salary * (taxable.getUpperTaxRate() / 100);
                salary -= upperTax;
                developer = new SeniorDeveloper(id, newDeveloper.getName(), salary);
                break;
            default:
                throw new IllegalArgumentException("Error");
        }

        developers.put(id, developer);
        return developer;
    }


    @PutMapping("/{id}")
    public Developer updateDeveloper(@PathVariable int id, @RequestBody Developer updatedDeveloper) {
        if (developers.containsKey(id)) {
            developers.put(id, updatedDeveloper);
            return updatedDeveloper;
        } else {
            throw new NoSuchElementException("Developer not found");
        }
    }


    @DeleteMapping("/{id}")
    public void deleteDeveloper(@PathVariable int id) {
        if (developers.containsKey(id)) {
            developers.remove(id);
        } else {
            throw new NoSuchElementException("Developer not found");
        }
    }
}
