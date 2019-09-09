
/**
 * @author Joseph Elsisi
 * Direction represents the direction a person object is facing 
 */
public enum Direction {
	N, E, S, W, none;
	
	/**
	 * @return the next direction to the right of the current direction
	 */
	public Direction cycle() { //turn this into a switch statment
		if(this == N) {
			return E;
		}
		else if(this == E){
			return S;
		}
		else if(this == S) {
			return W;
		}
		else if(this == W) {
			return N;
		}
		else {
			return none;
		}
	}
	/**
	 * @return the opposite direction of the current direction
	 */
	public Direction getOpposite(){
		if(this == N) {
			return S;
		}
		else if(this == E){
			return W;
		}
		else if(this == S) {
			return N;
		}
		else if(this == W) {
			return E;
		}
		else {
			return none;
		}
	}
		/**
		 * @param other is a given Direction
		 * @return true/false depending on whether the Direction is opposite to the current Direction
		 */
		public boolean isOpposite(Direction other) {
			if(this == N) {
				if(other.getOpposite() == N) {
					return true;
				}
			}
			else if(this == E){
				if(other.getOpposite() == E) {
					return true;
				}
			}
			else if(this == S) {
				if(other.getOpposite() == S) {
					return true;
				}
			}
			else if(this == W) {
				if(other.getOpposite() == W) {
					return true;
				}
			}
			else if(this == none) {
				if(other.getOpposite() == none) {
					return true;
				}
			}
			return false;
		}
	}
