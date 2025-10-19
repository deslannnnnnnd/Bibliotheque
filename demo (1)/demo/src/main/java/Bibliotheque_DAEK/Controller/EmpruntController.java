package Bibliotheque_DAEK.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import Bibliotheque_DAEK.Model.Emprunt;
import Bibliotheque_DAEK.Repository.EmpruntRepository;

@RestController
@RequestMapping("/api/emprunts")
@CrossOrigin(origins = "*")
public class EmpruntController {

    @Autowired
    private EmpruntRepository empruntRepository;

    // Récupérer tous les emprunts
    @GetMapping
    public ResponseEntity<List<Emprunt>> getAllEmprunts() {
        try {
            List<Emprunt> emprunts = empruntRepository.findAll();
            if (emprunts.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(emprunts, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Récupérer un emprunt par ID
    @GetMapping("/{id}")
    public ResponseEntity<Emprunt> getEmpruntById(@PathVariable("id") Long id) {
        Optional<Emprunt> empruntData = empruntRepository.findById(id);
        if (empruntData.isPresent()) {
            return new ResponseEntity<>(empruntData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Créer un nouvel emprunt
    @PostMapping
    public ResponseEntity<Emprunt> createEmprunt(@RequestBody Emprunt emprunt) {
        try {
            Emprunt newEmprunt = empruntRepository.save(emprunt);
            return new ResponseEntity<>(newEmprunt, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Mettre à jour un emprunt
    @PutMapping("/{id}")
    public ResponseEntity<Emprunt> updateEmprunt(@PathVariable("id") Long id, @RequestBody Emprunt emprunt) {
        Optional<Emprunt> empruntData = empruntRepository.findById(id);
        if (empruntData.isPresent()) {
            Emprunt existingEmprunt = empruntData.get();
            existingEmprunt.setUserId(emprunt.getUserId());
            existingEmprunt.setBookId(emprunt.getBookId());
            existingEmprunt.setDateEmprunt(emprunt.getDateEmprunt());
            existingEmprunt.setDateRetourPrevue(emprunt.getDateRetourPrevue());
            existingEmprunt.setDateRetourEffective(emprunt.getDateRetourEffective());
            existingEmprunt.setStatut(emprunt.getStatut());
            existingEmprunt.setAmende(emprunt.getAmende());
            return new ResponseEntity<>(empruntRepository.save(existingEmprunt), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Supprimer un emprunt
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteEmprunt(@PathVariable("id") Long id) {
        try {
            empruntRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Supprimer tous les emprunts
    @DeleteMapping
    public ResponseEntity<HttpStatus> deleteAllEmprunts() {
        try {
            empruntRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

