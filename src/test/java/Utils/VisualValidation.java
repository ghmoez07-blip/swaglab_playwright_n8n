package Utils;

import Steps.Hook;
import com.microsoft.playwright.Page;
import org.junit.Assert;
import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import nu.pattern.OpenCV;

import java.nio.file.Paths;

public class VisualValidation {

   /* static {
        // Charge la bibliothèque native au démarrage
        OpenCV.loadShared();
    }*/
    static {
        // Remplacement pour Java moderne (12 et +)
        nu.pattern.OpenCV.loadLocally();
    }

    /**
     * Vérifie si une image modèle est présente dans une capture d'écran
     * @return true si la similarité dépasse le seuil (ex: 0.9 pour 90%)
     */
    public static boolean isElementPresent(String screenshotPath, String templatePath, double threshold) {
        Mat source = Imgcodecs.imread(screenshotPath);
        Mat template = Imgcodecs.imread(templatePath);

        // SÉCURITÉ : Vérifier si les images sont chargées
        if (source.empty()) {
            throw new RuntimeException("Erreur : Impossible de lire la capture d'écran à : " + screenshotPath);
        }
        if (template.empty()) {
            throw new RuntimeException("Erreur : Image de référence introuvable à : " + templatePath);
        }

        Mat result = new Mat();
        // La comparaison échouait car 'template' était vide
        Imgproc.matchTemplate(source, template, result, Imgproc.TM_CCOEFF_NORMED);
        Core.MinMaxLocResult mmr = Core.minMaxLoc(result);

        // Ajoutez ceci dans VisualValidation.java pour voir le score réel dans la console
        System.out.println("Score de similarité détecté : " + mmr.maxVal);

        return mmr.maxVal >= threshold;
    }



    public static double compareImages(String expectedPath, String actualPath, String diffPath) {
        Mat img1 = Imgcodecs.imread(expectedPath);
        Mat img2 = Imgcodecs.imread(actualPath);

        // Les images doivent avoir la même taille
        if (img1.empty() || img2.empty() || img1.cols() != img2.cols() || img1.rows() != img2.rows()) {
            System.out.println("Erreur : Les images n'existent pas ou n'ont pas la même taille.");
            return 100.0; // 100% de différence
        }

        // Calcul de la différence absolue
        Mat diff = new Mat();
        Core.absdiff(img1, img2, diff);

        // Conversion en gris pour compter les pixels différents
        Mat grayDiff = new Mat();
        Imgproc.cvtColor(diff, grayDiff, Imgproc.COLOR_BGR2GRAY);

        // Seuil : tout ce qui n'est pas noir devient blanc (les différences)
        Imgproc.threshold(grayDiff, grayDiff, 10, 255, Imgproc.THRESH_BINARY);

        // Sauvegarder l'image des différences pour analyse humaine
        Imgcodecs.imwrite(diffPath, grayDiff);

        // Calcul du pourcentage de pixels différents
        int totalPixels = grayDiff.cols() * grayDiff.rows();
        int differentPixels = Core.countNonZero(grayDiff);

        // --- LIGNE À AJOUTER ---
        double percentage = (double) differentPixels / totalPixels * 100;
        System.out.println("📊 Pourcentage de différence visuelle : " + percentage + "%");
        // -----------------------

        return percentage;
    }



}