package com.cognizant.jpaas2.todo;

import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/todo")
@Controller
@RooWebScaffold(path = "todo", formBackingObject = TODO.class)
public class TODOController {
}
