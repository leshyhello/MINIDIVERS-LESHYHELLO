public class Patrol {
public static final int GRUNT_HEALTH = 100;

public int combinedHealth;
public int patrolMembers;
public String strongestMember;
public int strongestMemberHealth;
public int fabricators;

public final int startingMembers;
public boolean strongestAlive = true;
private int strongestCurrentHealth;
private int gruntHealthPool;

public Patrol(int health, int members, String strongest, int strongestHealth){
  this(health, members, strongest, strongestHealth, 0);
}

public Patrol(int health, int members, String strongest, int strongestHealth, int fabs){
  strongestMember = strongest;
  strongestMemberHealth = strongestHealth;
  strongestCurrentHealth = strongestHealth;
  gruntHealthPool = Math.max(health - strongestHealth, 0);
  fabricators = fabs;
  startingMembers = members;
  recount();
}

// Grunts soak damage first; anything left over hits the strongest member.
public void takeDamage(int damage){
  int toGrunts = Math.min(damage, gruntHealthPool);
  gruntHealthPool -= toGrunts;
  damage -= toGrunts;
  if (damage > 0 && strongestAlive) {
    strongestCurrentHealth -= damage;
    if (strongestCurrentHealth <= 0) {
      strongestCurrentHealth = 0;
      strongestAlive = false;
    }
  }
  recount();
}

// The railcannon always kills the strongest member. If it's already dead, it kills one grunt instead.
// Returns true if the strongest member was the one killed.
public boolean railcannonStrike(){
  if (strongestAlive) {
    strongestAlive = false;
    strongestCurrentHealth = 0;
    recount();
    return true;
  }
  gruntHealthPool -= Math.min(gruntHealthPool, GRUNT_HEALTH);
  recount();
  return false;
}

public void addGrunts(int count){
  gruntHealthPool += count * GRUNT_HEALTH;
  recount();
}

// Damage falls off with the square root of how much of the patrol is left,
// and drops further once the strongest member is dead.
public int scaleDamage(int baseDamage){
  if (patrolMembers <= 0) return 0;
  double multiplier = Math.sqrt((double) patrolMembers / startingMembers);
  if (!strongestAlive) multiplier *= 0.75;
  return Math.max(1, (int) Math.round(baseDamage * multiplier));
}

private void recount(){
  int grunts = (gruntHealthPool + GRUNT_HEALTH - 1) / GRUNT_HEALTH;
  patrolMembers = grunts + (strongestAlive ? 1 : 0);
  combinedHealth = gruntHealthPool + strongestCurrentHealth;
}

}
