import java.util.Scanner;

import static java.lang.Thread.sleep;

public class InfixToPostfix {
    static String infix;
    static String postfix;

    public static void main(String[] args) {
        while(true) {
            askMenu();
            Scanner answer = new Scanner(System.in);
            int num = answer.nextInt();
            if(num == 1) {
                getInfix();
                convertInfix();
                printResult();
                calculatePostfix();

                System.out.println();
                postfix = "";
                goodNight();
            }
            else if(num == 2){
                getPostfix();
                calculatePostfix();

                System.out.println();
                postfix = "";
                goodNight();
            }
        }
    }

    public static void askMenu(){
        System.out.println();
        System.out.println("***********************************");
        System.out.println("*******         MENU         ******");
        System.out.println("***********************************");
        System.out.println();
        System.out.println("1. Infix to Postfix");
        System.out.println();
        System.out.println("2. Calculate postfix");
        System.out.println();
        System.out.println("***********************************");
        System.out.println();
        System.out.print("Answer (Enter number 1 or 2) : ");
    }
    public static void goodNight(){
        try {
            sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void getInfix() {
        System.out.print("Enter Infix : ");
        Scanner input = new Scanner(System.in);
        infix = input.nextLine();
    }

    public static void getPostfix() {
        System.out.print("Enter Postfix : ");
        Scanner input = new Scanner(System.in);
        postfix = input.nextLine();
        infix = postfix;
    }

    public static void calculatePostfix(){
        String[] ch = postfix.split(" ");
        String first;
        String second;

        Stack myStackForCal = new Stack();

        for(int i=0; i<ch.length; i++){
            if(precedence(ch[i]) > 0){
                second = myStackForCal.stackPop();
                first = myStackForCal.stackPop();
                double s = Double.parseDouble(String.valueOf(second));
                double f = Double.parseDouble(String.valueOf(first));
                double result = 0;

                switch (ch[i]) {
                    case "+":
                        result = f + s;
                        break;
                    case "-":
                        result = f - s;
                        break;
                    case "*":
                        result = f * s;
                        break;
                    case "/":
                        result = f / s;
                        break;
                    case "^":
                        result = Math.pow(f, s);
                        break;
                    default:
                        break;
                }
                myStackForCal.stackPush(String.valueOf(result));
            }
            else {
                myStackForCal.stackPush(ch[i]);
            }
        }

        String result = myStackForCal.stackPop();
        System.out.println("Result : " + result);
    }

    public static void printResult(){
        System.out.println("Result : " + postfix);
    }

    public static void convertInfix(){
        Stack myStack = new Stack();
        myStack.stackPush("(");
        infix = infix + " " + ")";
        String[] ch = infix.split(" ");

        for(int i=0; i<ch.length; i++){

            if(ch[i].equals("(")) { myStack.stackPush(ch[i]); }
            else if(isNum(ch[i])){   // 현재 문자가 문자 혹은 숫자인 경우
                writePostfix(ch[i]);
            }
            else if(precedence(ch[i]) > 0){
                String x = myStack.stack[myStack.top];    // 스택 맨 위의 문자
                if(precedence(x) >= precedence(ch[i])){    // 스택 맨 위 문자와 현재 문자 중요도 비교 (스택 맨 위 문자가 연산 기호가 아닐 경우 중요도 0)
                    writePostfix(myStack.stackPop());   // 스택 맨 위 문자가 중요도가 더 높거나 같을 경우 pop
                    myStack.stackPush(ch[i]);              // pop 후 현재 문자를 스택에 push
                }
                else{ myStack.stackPush(ch[i]); }
            }
            else if(ch[i].equals(")")){
                String x = myStack.stackPop();
                while(!x.equals("(")){
                    writePostfix(x);
                    x = myStack.stackPop();
                }
            }
        }
    }

    static int precedence(String item){
        return switch (item) {
            case "^" -> 3;
            case "*", "/" -> 2;
            case "+", "-" -> 1;
            default -> 0;
        };
    }

    public static boolean isNum(String str){
        if(str==null) return false;
        try {
            double d = Double.parseDouble(str);
        } catch (NumberFormatException e){
            return false;
        }
        return true;
    }

    static void writePostfix(String item){
        if(postfix == null) postfix = item;
        else postfix = postfix + " " + item;
    }

    public static class Stack {
        int SIZE = infix.length();
        int top = -1;
        String[] stack = new String[SIZE];

        public void stackPush(String item) {
            top = top + 1;
            stack[top] = item;
        }

        public String stackPop(){
            String pop = stack[top];
            top= top - 1;
            return pop;
        }
    }
}
