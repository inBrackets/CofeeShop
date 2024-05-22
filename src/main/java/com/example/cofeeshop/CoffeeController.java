package com.example.cofeeshop;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/coffees")
public class CoffeeController {

    private List<Coffee> coffees = new ArrayList();

    private CoffeeController() {
        coffees.addAll(List.of(
                new Coffee("Lungnnnnno"),
                new Coffee("Nescafee")
                )
        );
    }

    @GetMapping
    Iterable<Coffee> getAllCoffees() {
        return coffees;
    }

    @PostMapping("/new")
    Coffee postCoffee(@RequestBody Coffee coffee) {
        coffees.add(coffee);
        return coffee;
    }

    @PutMapping("/{id}")
    Coffee putCoffee(@PathVariable String id, @RequestBody Coffee coffee) {
        int index = -1;
        for(Coffee c: coffees) {
            if(c.getId().equals(id)) {
                index = coffees.indexOf(c);
                coffees.set(index, coffee);
            }
        }
        return (index == -1) ? postCoffee(coffee) : coffee;
    }

    @DeleteMapping("/{id}")
    void deleteCoffee(@PathVariable String id) {
        coffees.removeIf(c->c.getId().equals(id));
    }
}
