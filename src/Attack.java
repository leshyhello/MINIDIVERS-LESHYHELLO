public class Attack {
public String stratagemCode;
public int attackDamage;
public int Stratagemdeviation;
public String attackName;

public Attack(String code, int damage, int dev){
  this(code, damage, dev, "");
}

public Attack(String code, int damage, int dev, String name){
  stratagemCode = code;
  attackDamage = damage;
  Stratagemdeviation = dev;
  attackName = name;
}

}
