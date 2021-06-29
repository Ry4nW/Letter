package IO;

// IOResult allows us to access the results of an IO Operation from {IO}.
public class IOResult<T> {

    private T data;
    private boolean check;

    public IOResult(boolean check, T data) {
        this.check = check;
        this.data = data;
    }

    public boolean checked() {
        return check;
    }

    public boolean hasData() {
        return data != null;
    }

    public T getData() {
        return data;
    }
}

