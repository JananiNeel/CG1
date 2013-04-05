import Jama.Matrix;

public class polygon {

	private float[] xs;
	private float[] ys;
	private int ns;
	private Matrix transforms;
	private Matrix[] pointMatricies;
	
	public polygon(float[] x, float[] y, int n, Matrix transform){
		xs = x;
		ys = y;
		ns = n;
		transforms = transform;
		Matrix[] pointMatricies = new Matrix[n];
		for(int i=0; i<n; i++){
			double[][] point = new double[3][];
			point[0] = new double[]{xs[i]};
			point[1] = new double[]{ys[i]};
			point[2] = new double[]{1};
			pointMatricies[i] = new Matrix(point);
		}
		setPointMatricies(pointMatricies);
	}
	
	public float[] getXs() {
		return xs;
	}

	public void setXs(float[] xs) {
		this.xs = xs;
	}

	public float[] getYs() {
		return ys;
	}

	public void setYs(float[] ys) {
		this.ys = ys;
	}

	public int getNs() {
		return ns;
	}

	public void setNs(int ns) {
		this.ns = ns;
	}

	public Matrix getTransforms() {
		return transforms;
	}

	public void setTransforms(Matrix transforms) {
		this.transforms = transforms;
	}

	public Matrix[] getPointMatrix() {
		return pointMatricies;
	}

	public void setPointMatricies(Matrix[] pointsMatrix) {
		this.pointMatricies = pointsMatrix;
	}
	
	public float[] getTransformXs() {
		float[] tXs = new float[ns];
		Matrix transform = transforms;
		for(int i=0; i<ns; i++){
			Matrix point = pointMatricies[i];
			Matrix transformed = transform.times(point);
			double[][] newPoints = transformed.getArray();
			tXs[i] = (float) newPoints[0][0];
		}
		return tXs;
	}
	public float[] getTransformYs() {
		float[] tYs = new float[ns];
		Matrix transform = transforms;
		for(int i=0; i<ns; i++){
			Matrix point = pointMatricies[i];
			Matrix transformed = transform.times(point);
			double[][] newPoints = transformed.getArray();
			tYs[i] = (float) newPoints[1][0];
		}
		return tYs;
	}
}
