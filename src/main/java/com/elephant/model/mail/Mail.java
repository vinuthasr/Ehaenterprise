package com.elephant.model.mail;

import java.util.List;
import java.util.Map;

import org.springframework.ui.Model;

public class Mail {

    private String from;
    private String to;
    private String subject;
    private List<Object> attachments;
    private Map<String, Object> model;
    private Model model1;
    public Model getModel1() {
		return model1;
	}

	public void setModel1(Model model1) {
		this.model1 = model1;
	}

	public Mail() {

    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public List<Object> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<Object> attachments) {
        this.attachments = attachments;
    }

    public Map<String, Object> getModel() {
        return model;
    }

    public void setModel(Map<String, Object> model) {
        this.model = model;
    }
}
