//package id.achfajar.challenge4.service;
//
//import id.achfajar.challenge4.model.USERR;
//import id.achfajar.challenge4.repository.USERRepository;
//import jakarta.transaction.Transactional;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//@Transactional
//public class USERService {
//
//    @Autowired
//    protected USERRepository USERRepository;
//
//    public USERR insertUsers(USERR USERR) {
//        return USERRepository.save(USERR);
//    }
//
//    public USERR updateUsers(Long id, USERR USERR) {
//        USERR.setId(id);
//        USERRepository.save(USERR);
//        return USERR;
//    }
//
//    public USERR getUsersById(Long id) {
//        return USERRepository.getById(id);
//    }
//
//    public void deleteUsers(Long id) {
//        USERR USERR = USERRepository.getById(id);
//        USERRepository.delete(USERR);
//    }
//
//    public List<USERR> getList() {
//        return USERRepository.findAll();
//    }
//}