package project.thegame;

import java.util.Random;
import java.util.Scanner;

public class TheGameDemo {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        TheGameDemo demo = new TheGameDemo();
        demo.first();
        demo.start();
    }

    void first(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("환영합니다.");
        System.out.println("발표 준비를 하다 잘못해서 시작하게 되었습니다.");
        System.out.println("'더 게임'을 시작합니다.");
        int select;
        while(true){
            System.out.println("============================================================");
            System.out.println("1. 게임설명 \t 2. 게임시작 \t 3. 게임종료");

            System.out.print("원하는 항목의 숫자를 입력해주세요: ");
            select = scanner.nextInt();
            System.out.println("============================================================");

            switch(select){
                case 1:
                    System.out.println("게임을 설명합니다.");
                    System.out.println("승리조건: 플레이어가 가진 모든 카드를 내려놓으면 승리합니다.");
                    System.out.println("1. 플레이어는 보충덱이 모두 없어질 때 까지 무조건 2장 이상의 카드를 내려놔야 합니다.");
                    System.out.println("2. 보충덱의 카드가 떨어지면 그 때부터는 반드시 한 장 이상 내려놓는 것으로 룰이 변경됩니다.");
                    System.out.println("3. [1 , 1 , 100 , 100]");
                    System.out.println("4. 1표시에는 오름차순, 100표시에는 내림차순으로만 카드를 내려놓을 수 있습니다.");
                    System.out.println("5. 단, 10차이가 나는 숫자는 점프할 수 있습니다.");
                    System.out.println("6. 예를들어, 1표시 위에 13, 14를 낸 뒤 4를 낼 수 있습니다.");
                    System.out.println("7. 반대로, 100표시 위에 86, 85를 낸 뒤 95를 낼 수 있습니다.");
                    continue;

                case 2:
                    System.out.println("게임을 시작합니다.");
                    break;

                case 3:
                    System.out.println("게임종료는 없습니다.");
                    continue;
            }
            break;
        }
    }

    void start(){
        System.out.println("카드를 내려 놓습니다.");
        System.out.println("[1 , 1 , 100 , 100]");
    }

}
