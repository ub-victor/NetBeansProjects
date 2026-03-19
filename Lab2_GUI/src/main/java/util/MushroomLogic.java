package util;

public class MushroomLogic {

    public static String identify(boolean gills, boolean forest, boolean ring, boolean convex) {

        if (!gills) {
            return "Cepe de bordeau";
        }

        if (gills && forest && !ring && !convex) {
            return "Girolle";
        }

        if (gills && !forest && ring && convex) {
            return "Agaric jaunissant";
        }

        if (gills && !forest && ring && !convex) {
            return "Coprin chevelu";
        }

        if (gills && forest && ring && convex) {
            return "Amanite tue-mouche";
        }

        if (gills && forest && !ring && convex) {
            return "Pied bleu";
        }

        return "Unknown";
    }
}