package trivia;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class QuestionRepo {
    private  final Map<Category, LinkedList<String>> questions = new HashMap<>();

    public void init(){
        for(Category cat: Category.values()){
            questions.put(cat, initQuestionsBaseForCategory(cat));
        }
    }

    public void askQuestion(Category currentCategory) {
        System.out.println(questions.get(currentCategory).removeFirst());
    }

    private LinkedList<String> initQuestionsBaseForCategory(Category cat){
        LinkedList<String> categoryQuestions = new LinkedList<>();
        for (int i = 0; i < 50; i++) {
            categoryQuestions.addLast(cat.categoryName+" Question " + i);
        }
        return categoryQuestions;
    }
}
