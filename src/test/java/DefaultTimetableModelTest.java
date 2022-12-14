import com.github.UniHelper.model.timetable.DefaultTimetableModel;
import com.github.UniHelper.model.timetable.TimetableModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.image.BufferedImage;

public class DefaultTimetableModelTest {

    private TimetableModel timetableModel;

    @BeforeEach
    void initialize() {
        timetableModel = new DefaultTimetableModel();
    }

    @Test
    void accessors_test(){
        // Given
        BufferedImage input1 = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
        BufferedImage input2 = new BufferedImage(2, 2, BufferedImage.TYPE_3BYTE_BGR);
        BufferedImage input3 = null;

        // When
        timetableModel.setTimetableImage(input1);
        BufferedImage output1 = timetableModel.getTimetableImage();
        timetableModel.setTimetableImage(input2);
        BufferedImage output2 = timetableModel.getTimetableImage();
        timetableModel.setTimetableImage(input3);
        BufferedImage output3 = timetableModel.getTimetableImage();

        // Then
        Assertions.assertTrue(areImagesEqual(input1, output1));
        Assertions.assertTrue(areImagesEqual(input2, output2));
        Assertions.assertTrue(areImagesEqual(input3, output3));
    }

    @Test
    void deleteTimetable_test() {
        // Given
        BufferedImage input1 = null;
        BufferedImage input2 = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);

        // When
        timetableModel.setTimetableImage(input1);
        timetableModel.deleteTimetable();
        BufferedImage output1 = timetableModel.getTimetableImage();

        timetableModel.setTimetableImage(input2);
        timetableModel.deleteTimetable();
        BufferedImage output2 = timetableModel.getTimetableImage();

        // Then
        Assertions.assertNull(output1);
        Assertions.assertNull(output2);
    }

    private static boolean areImagesEqual(BufferedImage imgA, BufferedImage imgB) {
        if(imgA == null || imgB == null)
            return imgA == imgB;

        if (imgA.getWidth() != imgB.getWidth() || imgA.getHeight() != imgB.getHeight()) {
            return false;
        }

        int width  = imgA.getWidth();
        int height = imgA.getHeight();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (imgA.getRGB(x, y) != imgB.getRGB(x, y)) {
                    return false;
                }
            }
        }
        return true;
    }
}
