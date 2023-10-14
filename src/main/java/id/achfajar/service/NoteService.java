package id.achfajar.service;

import id.achfajar.controller.Controller;
import id.achfajar.model.Note;
import id.achfajar.model.Data;
import id.achfajar.utils.Utils;
import id.achfajar.view.MenuView;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import static id.achfajar.model.Data.notesPerItem;

public class NoteService {
    protected static void handleNotes(String foodName, byte quantity){
        if (quantity > 0){
            NoteService.addNotes(foodName,quantity);
        } else {
            Controller.home();
        }
    }
    public static void addNote(String foodName, byte quantity, String notes){
        Note notesItem = new Note();
        notesItem.setName(foodName);
        notesItem.setQuantity(quantity);
        notesItem.setNote(notes);
        Data.notesPerItem.add(notesItem);
    }
    public static void addNotes(String foodName, byte quantity) {
        MenuView.addNotes();
        MenuView.inputOption();
        String newNote = inputOption();
        NoteService addNotes = new NoteService();
        addNotes.addNote(foodName, quantity, newNote);
    }
    public static String getAllNotesItems(){
        removeNotesIfEmpty();
        StringBuilder sb = new StringBuilder();
        sb.append(hideIfEmpty());
        for (Note note : notesPerItem) {
            sb.append(Utils.NEW_LINE+
                    note.getName()
                    +Utils.SPACE
                    +note.getQuantity()
                    +Utils.SPACE
                    +note.getNote());
        }
        return String.valueOf(sb);
    }
    public static String hideIfEmpty(){
        StringBuilder sb = new StringBuilder();
        if (notesPerItem.isEmpty()){
            sb.append(MenuView.hideNotes());
        } else {
            sb.append(MenuView.notes());
        }
        return String.valueOf(sb);
    }
    protected static void removeNotesIfEmpty() {
        List<Note> noteToRemove = new ArrayList<>();
        for (Note note : notesPerItem) {
            String noteValue = note.getNote();
            if (noteValue.equals("0") || noteValue.isEmpty()) {
                noteToRemove.add(note);
            }
        }
        for (Note note : noteToRemove) {
            removeNoteItem(note.getName());
        }
    }
    public static void removeNoteItem(String foodName) {
        notesPerItem.removeIf(note -> note.getName().equals(foodName) && note.getNote().equals("0") || note.getNote().isEmpty());
    }
    public static String inputOption() {
        Scanner scanner = new Scanner(System.in);
        try {
            String input = scanner.nextLine();
            return input;
        } catch (InputMismatchException e) {
            scanner.nextLine();
            return inputOption(); // rekursif
        }
    }
}
