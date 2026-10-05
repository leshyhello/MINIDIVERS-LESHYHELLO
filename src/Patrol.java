public class Patrol {
public int combinedHealth;
public int patrolMembers;
public String strongestMember;
public int strongestMemberHealth;
public int fabricators;

public Patrol(int health, int members, String strongest, int strongestHealth){
  this(health, members, strongest, strongestHealth, 0);
}

public Patrol(int health, int members, String strongest, int strongestHealth, int fabs){
  combinedHealth = health;
  patrolMembers = members;
  strongestMember = strongest;
  strongestMemberHealth = strongestHealth;
  fabricators = fabs;
}

}
