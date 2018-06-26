package httpclient;

public class UnexpectedResultException extends RuntimeException
{
	private static final long serialVersionUID = 1L;

	public UnexpectedResultException()
	{
		super();
	}

	public UnexpectedResultException(String message)
	{
		super(message);
	}
	
	public UnexpectedResultException(String message, Throwable cause)
	{
		super(message, cause);
	}
}
