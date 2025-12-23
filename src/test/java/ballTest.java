import org.example.Ball;
import org.junit.jupiter.api.Test;
// Change this import to the Jupiter version for JUnit 5
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ballTest {

    // Default values to set the fields if client does not set manually
    public static final int DEFAULT_X = 50;
    public static final int DEFAULT_Y = 50;
    public static final int DEFAULT_SPEEDX = 2;
    public static final int DEFAULT_SPEEDY = 2;
    public static final int DEFAULT_BALLSIZE = 100;

    public static final int MAX_WIDTH = 500;
    public static final int MAX_HEIGHT = 500;

    @Test
    public void testConstructor1() {
        int ballX = -1;
        int ballY = 1;
        int ballSize = 1;
        int ballSpeedX = 1;
        int ballSpeedY = 1;
        assertThrows(IllegalArgumentException.class, () -> new Ball(ballX, ballY, ballSpeedX, ballSpeedY, ballSize));
    }

    @Test
    public void testConstructor2() {
        int ballX = 1;
        int ballY = -1;
        int ballSize = 1;
        int ballSpeedX = 1;
        int ballSpeedY = 1;
        assertThrows(IllegalArgumentException.class, () -> new Ball(ballX, ballY, ballSpeedX, ballSpeedY, ballSize));

    }

    @Test
    public void testConstructor3() {
        int ballX = 1;
        int ballY = 1;
        int ballSize = -1;
        int ballSpeedX = 1;
        int ballSpeedY = 1;
        assertThrows(IllegalArgumentException.class, () -> new Ball(ballX, ballY, ballSpeedX, ballSpeedY, ballSize));

    }

    @Test
    public void testConstructor4() {
        int ballX = 1;
        int ballY = 1;
        int ballSize = 1;
        int ballSpeedX = -11;
        int ballSpeedY = 1;
        assertThrows(IllegalArgumentException.class, () -> new Ball(ballX, ballY, ballSpeedX, ballSpeedY, ballSize));

    }

    @Test
    public void testConstructor5() {
        int ballX = 1;
        int ballY = 1;
        int ballSize = 1;
        int ballSpeedX = 1;
        int ballSpeedY = -11;
        assertThrows(IllegalArgumentException.class, () -> new Ball(ballX, ballY, ballSpeedX, ballSpeedY, ballSize));

    }

    @Test
    public void testConstructor6() {
        int ballX = 1;
        int ballY = 1;
        int ballSize = 1;
        int ballSpeedX = 2;
        int ballSpeedY = 2;
        Ball bouncingBall = new Ball(ballX, ballY, ballSpeedX, ballSpeedY, ballSize);

        assertEquals(ballX, bouncingBall.getBallX());
        assertEquals(ballY, bouncingBall.getBallY());
        assertEquals(ballSize, bouncingBall.getBallSize());
        assertEquals(ballSpeedX, bouncingBall.getBallSpeedX());
        assertEquals(ballSpeedY, bouncingBall.getBallSpeedY());

    }

    @Test
    public void testConstructor7() {
        Ball bouncingBall = new Ball();
        assertEquals(DEFAULT_X, bouncingBall.getBallX());
        assertEquals(DEFAULT_Y, bouncingBall.getBallY());
        assertEquals(DEFAULT_SPEEDX, bouncingBall.getBallSpeedX());
        assertEquals(DEFAULT_SPEEDY, bouncingBall.getBallSpeedY());
        assertEquals(100, bouncingBall.getBallSize());
    }


}
