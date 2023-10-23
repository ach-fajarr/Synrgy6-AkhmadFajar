//package id.achfajar.challenge4.controller;
//
//import id.achfajar.challenge4.model.USERR;
//import id.achfajar.challenge4.service.USERService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//public class USERRController {
//
//    @Autowired
//    protected USERService USERService;
//
//    @PostMapping("/")
//    public ResponseEntity<USERR> insertUsers(@RequestBody USERR USERR) {
//        USERService.insertUsers(USERR);
//        return ResponseEntity.status(HttpStatus.CREATED).body(USERR);
//    }
//
//    @PutMapping("/{id}")
//    public ResponseEntity<USERR> updateUsers(@PathVariable Long id, @RequestBody USERR USERR) {
//        USERService.updateUsers(id, USERR);
//        return ResponseEntity.status(HttpStatus.CREATED).body(USERR);
//    }
//    @GetMapping("/{id}")
//    public ResponseEntity getUserById(@PathVariable Long id) {
//        USERR user = USERService.getUsersById(id);
//        return ResponseEntity.status(HttpStatus.OK).body(user);
//    }
//    @DeleteMapping("/{id}")
//    public ResponseEntity deleteUsers(@PathVariable Long id) {
//        USERService.deleteUsers(id);
//        return ResponseEntity.status(HttpStatus.OK).body("deleted");
//    }
//
//    @GetMapping("/")
//    public ResponseEntity<List<USERR>> getUsersList() {
//        List<USERR> users = USERService.getList();
//        return ResponseEntity.status(HttpStatus.OK).body(users);
//    }
//}
