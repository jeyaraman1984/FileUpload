package com.fileupload.controllers;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fileupload.models.FileDetails;
import com.fileupload.services.FileService;

@Controller
@RequestMapping(value = "/files")
public class FileController {

    @Autowired
    private FileService fileService;

    @GetMapping
    @ResponseBody
    public HttpEntity<List<FileDetails>> getAllFiles() {
        List<FileDetails> allFilesList = fileService.getAll();

        if (!allFilesList.isEmpty()) {
            for (FileDetails file : allFilesList) {
                Link selfLink = linkTo(methodOn(FileController.class).getFile(file.getFileId())).withSelfRel();
                file.add(selfLink);
            }
        }
        return new ResponseEntity<List<FileDetails>>(allFilesList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @ResponseBody
    public HttpEntity<FileDetails> getFile(@PathVariable Long id) {
        FileDetails file = fileService.getById(id);

        if (file == null) {
            return new ResponseEntity<FileDetails>(HttpStatus.NOT_FOUND);
        }

        Link selfLink = linkTo(methodOn(FileController.class).getFile(file.getFileId())).withSelfRel();
        file.add(selfLink);

        return new ResponseEntity<FileDetails>(file, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @ResponseBody
    public HttpEntity<FileDetails> updateFile(@PathVariable Long id, @RequestBody FileDetails file) {
        if (file == null) {
            return new ResponseEntity<FileDetails>(HttpStatus.NOT_FOUND);
        }

        file.setFileId(id);
        fileService.update(file);

        Link selfLink = linkTo(methodOn(FileController.class).getFile(file.getFileId())).withSelfRel();
        file.add(selfLink);

        return new ResponseEntity<FileDetails>(file, HttpStatus.OK);
    }

    @GetMapping("/upload")
    public String uploadFile() {
        return "FileUpload";
    }

    @PostMapping("/upload")
    public String handleFileUpload(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {

        FileDetails uploadedFile = fileService.processUploadedFile(file);

        if (uploadedFile != null) {
            redirectAttributes.addAttribute("id", uploadedFile.getFileId())
                              .addFlashAttribute("message",
                    "You successfully uploaded " + file.getOriginalFilename() + "!");

            return "redirect:/files/{id}";
        } else {
            return "redirect:/";
        }
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public HttpEntity<FileDetails> deleteFile(@PathVariable Long id) {
        FileDetails file = fileService.getById(id);

        if (file == null) {
            return new ResponseEntity<FileDetails>(HttpStatus.NOT_FOUND);
        }
        fileService.deleteById(id);
        return new ResponseEntity<FileDetails>(HttpStatus.NO_CONTENT);
    }

}
