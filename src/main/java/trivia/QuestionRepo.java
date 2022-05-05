package trivia;

import java.util.LinkedList;

public class QuestionRepo {


    LinkedList popQuestions = new LinkedList();
    LinkedList scienceQuestions = new LinkedList();
    LinkedList sportsQuestions = new LinkedList();
    LinkedList rockQuestions = new LinkedList();

    public void init(){
        for (int i = 0; i < 50; i++) {
            popQuestions.addLast("Pop Question " + i);
            scienceQuestions.addLast(("Science Question " + i));
            sportsQuestions.addLast(("Sports Question " + i));
            rockQuestions.addLast(("Rock Question " + i));
        }
    }

    public void askQuestion(Category currentCategory) {
        switch(currentCategory){
            case POP: System.out.println(popQuestions.removeFirst()); break;
            case SCIENCE: System.out.println(scienceQuestions.removeFirst()); break;
            case SPORTS: System.out.println(sportsQuestions.removeFirst()); break;
            case ROCK: System.out.println(rockQuestions.removeFirst());
        }
    }
}
