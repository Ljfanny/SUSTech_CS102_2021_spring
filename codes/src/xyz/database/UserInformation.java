package xyz.database;

public class UserInformation {

    public String id = "";
    public String name = "";

    public int exp = 0;

    public int level = 1;

    public int playerConut;
    public int aiCountEasy;
    public int aiCountMiddle;
    public int aiCountDifficult;
    public int personCountEasy;
    public int personCountMiddle;
    public int personCountDifficult;
    public int playerEasy;
    public int playerMiddle;
    public int playerDifficult;
    public int aiEasy;
    public int aiMiddle;
    public int aiDifficult;
    public int personEasy;
    public int personMiddle;
    public int personDifficult;

    double winRatePlayer;
    double winRatePersonEasy;
    double winRatePersonMiddle;
    double winRatePersonDifficult;
    double winRateAiEasy;
    double winRateAiMiddle;
    double winRateAiDifficult;
    double totalWinRate;

    UserInformation(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public UserInformation(String id, String name, int playerConut, int aiCountEasy, int aiCountMiddle, int aiCountDifficult, int personCountEasy, int personCountMiddle, int personCountDifficult, int playerEasy, int playerMiddle, int playerDifficult, int aiEasy, int aiMiddle, int aiDifficult, int personEasy, int personMiddle, int personDifficult) {
        this.id = id;
        this.name = name;
        this.playerConut = playerConut;
        this.aiCountEasy = aiCountEasy;
        this.aiCountMiddle = aiCountMiddle;
        this.aiCountDifficult = aiCountDifficult;
        this.personCountEasy = personCountEasy;
        this.personCountMiddle = personCountMiddle;
        this.personCountDifficult = personCountDifficult;
        this.playerEasy = playerEasy;
        this.playerMiddle = playerMiddle;
        this.playerDifficult = playerDifficult;
        this.aiEasy = aiEasy;
        this.aiMiddle = aiMiddle;
        this.aiDifficult = aiDifficult;
        this.personEasy = personEasy;
        this.personMiddle = personMiddle;
        this.personDifficult = personDifficult;
        calExp();
    }

    void calExp() {
        exp = personMiddle * 10 + personEasy*2 + personDifficult * 40;
        exp += aiCountDifficult * 50 + aiCountMiddle * 5 + aiCountEasy * 2;
        exp += playerEasy*3+playerMiddle*15+playerDifficult*40;
        winRateAiDifficult= aiDifficult/(double)aiCountDifficult;
        winRateAiMiddle= aiMiddle/(double)aiCountMiddle;
        winRateAiEasy= aiEasy/(double)aiCountEasy;
        winRatePlayer=(playerEasy+playerMiddle+playerDifficult)/(double)playerConut;
        winRatePersonDifficult=personDifficult/(double)personCountDifficult;
        winRatePersonMiddle=personMiddle/(double)personCountMiddle;
        winRatePersonEasy=personEasy/(double)personCountEasy;
        totalWinRate=100*(personEasy+personMiddle+personDifficult+aiEasy+aiMiddle+aiDifficult+playerEasy+playerDifficult+playerMiddle)/(double)(personCountEasy+personCountMiddle+personCountDifficult+playerConut+aiCountEasy+aiCountMiddle+aiDifficult);
        while (10*level*(level+1)/2<=exp)
            level++;
        System.out.println(this);
    }

    @Override
    public String toString() {
        return "UserInformation{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", exp=" + exp +
                ", level=" + level +
                ", playerConut=" + playerConut +
                ", aiCountEasy=" + aiCountEasy +
                ", aiCountMiddle=" + aiCountMiddle +
                ", aiCountDifficult=" + aiCountDifficult +
                ", personCountEasy=" + personCountEasy +
                ", personCountMiddle=" + personCountMiddle +
                ", personCountDifficult=" + personCountDifficult +
                ", playerEasy=" + playerEasy +
                ", playerMiddle=" + playerMiddle +
                ", playerDifficult=" + playerDifficult +
                ", aiEasy=" + aiEasy +
                ", aiMiddle=" + aiMiddle +
                ", aiDifficult=" + aiDifficult +
                ", personEasy=" + personEasy +
                ", personMiddle=" + personMiddle +
                ", personDifficult=" + personDifficult +
                ", winRatePlayer=" + winRatePlayer +
                ", winRatePersonEasy=" + winRatePersonEasy +
                ", winRatePersonMiddle=" + winRatePersonMiddle +
                ", winRatePersonDifficult=" + winRatePersonDifficult +
                ", winRateAiEasy=" + winRateAiEasy +
                ", winRateAiMiddle=" + winRateAiMiddle +
                ", winRateAiDifficult=" + winRateAiDifficult +
                ", totalWinRate=" + totalWinRate +
                '}';
    }

    public String detail(){
        StringBuilder info = new StringBuilder();
        info.append("账号                   : "+id);
        info.append("\n昵称                   : "+name);
        info.append("\n等级                   : "+level);
        info.append("\n经验                   : "+exp);
        info.append("\n距下一等级还需经验       ： "+ (10*(level)*(level+1)/2-exp));
        info.append("\n单机模式（简单） 胜场/局数： "+personEasy+"/"+personCountEasy);
        info.append("\n单机模式（中等） 胜场/局数： "+personMiddle+"/"+personCountMiddle);
        info.append("\n单机模式（困难） 胜场/局数： "+personDifficult+"/"+personCountDifficult);
        info.append("\n人机对战（简单） 胜场/局数： "+aiEasy+"/"+aiCountEasy);
        info.append("\n人机对战（中等） 胜场/局数： "+aiMiddle+"/"+aiCountMiddle);
        info.append("\n人机对战（困难） 胜场/局数： "+aiDifficult+"/"+aiDifficult);
        info.append("\n玩家对抗 (易|中|难)       胜场/局数： "+playerEasy+"|"+playerMiddle+"|"+playerDifficult+"/"+playerConut);
        return info.toString();
    }
}
