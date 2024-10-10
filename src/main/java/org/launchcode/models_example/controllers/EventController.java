package org.launchcode.codingevents.controllers;

import org.launchcode.models_example.data.EventData;
import org.launchcode.models_example.models.Event;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("events")
public class EventController {

    @GetMapping
    public String displayAllEvents(Model model) {
        model.addAttribute("title", "All Events");
        model.addAttribute("events", EventData.getAll());
        return "events/index";
    }

    @GetMapping("create")
    public String renderCreateEventForm(Model model) {
        model.addAttribute("title", "Create Event");
        return "events/create";
    }

    @PostMapping("create")
    public String processCreateEventForm(@ModelAttribute Event newEvent) {
        EventData.add(newEvent);
        return "redirect:/events";

    }

    @GetMapping("delete")
    public String displayDeleteEventForm(Model model) {
        model.addAttribute("title", "Delete Events");
        model.addAttribute("events", EventData.getAll());
        return "events/delete";
    }

    @PostMapping("delete")
    public String processDeleteEventsForm(@RequestParam(required=false) int[] eventIds) {
        if (eventIds != null) {
            for (int id:eventIds) {
                EventData.remove(id);
            }
        }
        return "redirect:/events";
    }

    @GetMapping("edit/{eventId}")
    public String displayEditForm(Model model, @PathVariable int eventId){
        Event edit = EventData.getById(eventId);
        model.addAttribute("event", edit);
        String title = "Edit Event " + edit.getName() + " (id=" + edit.getId() + ")";
        model.addAttribute("title", title);
        return "events/edit";

    }

    @PostMapping("edit")
    public String processEditForm(int eventId, String name, String description) {
    Event edit=EventData.getById(eventId);
    edit.setName(name);
    edit.setDescription(description);
    return "redirect:/events";
    }
}

