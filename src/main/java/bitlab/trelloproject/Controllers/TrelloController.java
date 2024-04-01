package bitlab.trelloproject.Controllers;

import bitlab.trelloproject.Entities.Comments;
import bitlab.trelloproject.Entities.Folders;
import bitlab.trelloproject.Entities.TaskCategories;
import bitlab.trelloproject.Entities.Tasks;
import bitlab.trelloproject.Repositories.CategoryRepository;
import bitlab.trelloproject.Repositories.CommentRepository;
import bitlab.trelloproject.Repositories.FolderRepository;
import bitlab.trelloproject.Repositories.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.config.Task;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Controller
@RequestMapping(value = "/main")
@RequiredArgsConstructor
public class TrelloController {

    private final FolderRepository folderRepository;

    private final TaskRepository taskRepository;

    private final CategoryRepository categoryRepository;

    private final CommentRepository commentRepository;

    @GetMapping(value = "/")
    public String getIndex(Model model){
        model.addAttribute("folders", folderRepository.findAll());
        return "index";
    }

    @PostMapping(value = "/addFolder")
    public String addFolder(@RequestParam(name = "folder") String folder, Model model){
        Folders folders = new Folders();
        folders.setName(folder);
        folderRepository.save(folders);

        return "redirect:/main/";
    }

    @PostMapping(value = "/deleteFolder")
    public String deleteFolder(@RequestParam(name = "folder_Id") Long folder_Id){
        Folders folders = folderRepository.findById(folder_Id).orElseThrow();
        List<Tasks> tasks = taskRepository.findByFolder(folders);
        for(Tasks tasks1 : tasks){
            taskRepository.delete(tasks1);
            List<Comments> comments = commentRepository.findByTask(tasks1);
            for (Comments comments1 : comments){
                commentRepository.delete(comments1);
            }
        }
        folderRepository.delete(folders);

        return "redirect:/main/";
    }

    @GetMapping(value = "/taskDashboard/{id}")
    public String getTaskDashboard(@PathVariable(name = "id") Long id, Model model){
        Folders folders = folderRepository.findById(id).orElseThrow();
        model.addAttribute("taskDashFolder", folders);

        model.addAttribute("categories", categoryRepository.findAll());

        model.addAttribute("taskDash", taskRepository.findByFolder(folders));

        model.addAttribute("viewCategory", folders.getCategoriesList());
        return "taskDashboard";
    }

    @PostMapping(value = "/addTask")
    public String addTask(@RequestParam(name = "title") String title,
                          @RequestParam(name = "description") String description,
                          @RequestParam(name = "id") Long id){
        Tasks tasks = new Tasks();
        tasks.setTitle(title);
        tasks.setDescription(description);
        tasks.setStatus(0);

        Folders folders =folderRepository.findById(id).orElseThrow();
        tasks.setFolder(folders);

        taskRepository.save(tasks);
        return "redirect:/main/taskDashboard/" + id;
    }

    @PostMapping(value = "/addCategory")
    public String addCategory(@RequestParam(name = "id") Long folderId,
                              @RequestParam(name = "category") Long categoryName,
                              Model model) {
       Folders folders = folderRepository.findById(folderId).orElseThrow();
       model.addAttribute("folder", folders);

       List<TaskCategories> categories = folders.getCategoriesList();
       TaskCategories categories1 = categoryRepository.findById(categoryName).orElseThrow();
       categories.add(categories1);
       folderRepository.save(folders);

        return "redirect:/main/taskDashboard/" + folderId;
    }

    @PostMapping(value = "/deleteCategory")
    public String deleteCategory(@RequestParam(name = "folderId") Long folderId,
                                 @RequestParam(name = "categoryId") Long categoryId){
        Folders folders = folderRepository.findById(folderId).orElseThrow();

        List<TaskCategories> categories = folders.getCategoriesList();
        TaskCategories categories1 = categoryRepository.findById(categoryId).orElseThrow();
        categories.remove(categories1);
        folderRepository.save(folders);

        return "redirect:/main/taskDashboard/" + folderId;
    }

    @GetMapping(value = "/detailTask/{id}")
    public String detailTask(@PathVariable(name = "id") Long taskId, Model model){
        Tasks task = taskRepository.findById(taskId).orElseThrow();
        model.addAttribute("taskDetail", task);

        List<Comments> comments = commentRepository.findByTask(task);
        model.addAttribute("comments", comments);

        return "taskDetail";
    }

    @PostMapping(value = "/updateTask")
    public String updateTask(@RequestParam(name = "title") String title,
                             @RequestParam(name = "description") String description,
                             @RequestParam(name = "id") Long id,
                             @RequestParam(name = "idTask") Long idTask,
                             @RequestParam(name = "status") String status){
        Tasks task = taskRepository.findById(idTask).orElseThrow();
        task.setTitle(title);
        task.setDescription(description);
        task.setStatus(Integer.parseInt(status));

        Folders folders = folderRepository.findById(id).orElseThrow();
        task.setFolder(folders);

        taskRepository.save(task);
        return "redirect:/main/taskDashboard/" + id;
    }

    @PostMapping(value = "/deleteTask")
    public String deleteTask(@RequestParam(name = "id") Long id,
                             @RequestParam(name = "idTask") Long idTask){
        Tasks tasks = taskRepository.findById(idTask).orElseThrow();
        List<Comments> comments = commentRepository.findByTask(tasks);
        for (Comments comments1 : comments){
            commentRepository.delete(comments1);
        }
       taskRepository.delete(tasks);

        return "redirect:/main/taskDashboard/" + id;
    }

    @PostMapping(value = "/addComment")
    public String addComment(@RequestParam(name = "id") Long taskId,
                             @RequestParam(name = "comment") String comment){
        Comments comments = new Comments();
        comments.setComment(comment);

        Tasks tasks = taskRepository.findById(taskId).orElseThrow();
        comments.setTask(tasks);

        commentRepository.save(comments);

        return  "redirect:/main/detailTask/" + taskId;
    }

}
