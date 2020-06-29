# AntColonySimulation

Ant Colony Simulation written in Java. The colony consists of a 27x27 square grid with the Queen Ant at the center of the grid.

Types of ants:
- Queen ant (yellow): Mother of all ants in the colony (except Bala ants). All other ants are born in the center square of the colony, where she lives. When the queen dies (from starvation, old age, or an attack by Bala ants) the simulation ends.
- Forager (green): Foragers find food in the parts of the colony that have been previously explored by Scouts. They bring food back to the Queen, leaving behind a trail of pheromones on their way. Other Foragers can detect these pheromones and follow them until there is no more food to be found on the given pheromone trail.
- Soldier (black): Soldier ants protect the queen. Soldiers move randomly until they encounter a Bala (enemy) ant, at which point they fight the Bala. When there is a fight, either the Bala ant or the Soldier will die -- they fight to the death every time.
- Scouts (blue): Scouts explore the colony and open up grid squares so that they are accessible to Foragers and Soldiers. Only Scouts (and Balas) can explore squares that have not been entered by a Scout previously.
- Balas (red): Bala ants are enemy ants: they are born in the corners of the grid, as opposed to the center. They move randomly until they encounter a non-Bala ant, and they will kill any non-Bala ant they come upon (except for Soldier ants, which are able to defend themselves and kill Balas sometimes).

- All ants (aside from the Queen) live for one year

- Grid squares are color coded depending on how much pheromone is present in the square, which alerts Forager ants to the presence of food

To run program -- download the files and compile/run Simulation.java in src directory.
