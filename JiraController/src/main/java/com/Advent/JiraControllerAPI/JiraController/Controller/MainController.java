package com.Advent.JiraControllerAPI.JiraController.Controller;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.Advent.JiraServiceAPI.JiraService.MainService.MainService;



@Controller
public class MainController {
	@Autowired
	MainService mainService;
	
    @GetMapping("/createIssue")
    @ResponseBody
    public void createIssueMethod() {
    	try
    	{
    		mainService.createIssue();
    	}catch (Exception e) {
			System.out.println("createIssueController exception " + e);
		}
    }
    
    @GetMapping("/createComment")
    @ResponseBody
    public void createCommentMethod() {
    	try
    	{	
    		mainService.createComment();
    	}catch (Exception e) {
			System.out.println("createCommentController exception " + e);
		}	
    }
    @GetMapping("/addAttachment")
    @ResponseBody
    public void addAttachmentMethod () {
    	try
    	{
    		mainService.addAttachment(new File("C:\\Users\\dell\\Desktop\\data.txt"));
    	}catch (Exception e) {
			System.out.println("addAttachmentController exception " + e);
		}
    }
    
}
