package com.brain.domain.task;

import com.brain.domain.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 场景
 * ClassName: Scene
 * Package: com.brain.pojo.Task
 * Description:
 *
 * @Author 皮皮dog
 * @Create 2023/4/16 12:20
 * @Version 1.0
 * @date 2023/04/16
 */
public class Scene implements Task{


    private List<MyTask> taskList;

    private String SceneName;





    public Scene(){}

    public Scene(String SceneName,List<MyTask> taskList) {
        this.SceneName = SceneName;
        this.taskList = taskList;
    }


    public void execute() {
        for (MyTask task : taskList) {
            task.execute();
        }
    }


    public List<MyTask> getTaskList() {
        return taskList;
    }

    public void setTaskList(List<MyTask> taskList) {
        this.taskList = taskList;
    }

    public String getSceneName() {
        return SceneName;
    }

    public void setSceneName(String sceneName) {
        SceneName = sceneName;
    }

    @Override
    public String toString() {
        return "Scene{" +
                "taskList=" + taskList +
                ", SceneName='" + SceneName + '\'' +
                '}';
    }

    public String toUpFile(){
        StringBuffer buffer = new StringBuffer();
        buffer.append("[");
        getTaskList().stream().forEach(task ->{
           buffer.append(task.toUpFile() + "&");
        });
        buffer.append("]");

        return "{" +
                getSceneName() + ":" +
                buffer +
                "}";
    }
}
