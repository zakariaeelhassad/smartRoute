package org.example.smartroute.controllers;

import jakarta.validation.Valid;
import org.example.smartroute.entities.DTO.tour.CreateTourDto;
import org.example.smartroute.entities.DTO.tour.TourDto;
import org.example.smartroute.entities.DTO.tour.UpdateTourDto;
import org.example.smartroute.services.ITourService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tours")
public class TourController {

    private ITourService service ;

    public TourController(ITourService service){
        this.service = service ;
    }

    @PostMapping
    public ResponseEntity<TourDto> create(@RequestBody @Valid CreateTourDto createTouDto){
        TourDto createTour = service.create(createTouDto);
        return ResponseEntity.ok(createTour);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TourDto> update(@PathVariable("id") Long id , @RequestBody @Valid UpdateTourDto updateTourDto){
        TourDto updateTour = service.update(id , updateTourDto);
        return ResponseEntity.ok(updateTour);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id){
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping
    public ResponseEntity<List<TourDto>> getAllTours(){
        List<TourDto> tours = service.getAll();
        return new ResponseEntity<>(tours , HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TourDto> getById(@PathVariable("id") Long id){
        TourDto getTourById = service.getById(id);
        return new ResponseEntity<>(getTourById , HttpStatus.OK);
    }

    @PostMapping("/{id}/optimize")
    public ResponseEntity<String> optimizeTour(@PathVariable Long id) {
        service.optimizeTour(id);
        return ResponseEntity.ok("Tour optimized successfully!");
    }
}
