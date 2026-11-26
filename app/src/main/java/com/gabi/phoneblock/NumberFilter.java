package com.gabi.phoneblock;

/** Decides whether a caller's number counts as starting with +346. */
final class NumberFilter {
    static boolean isBlocked(String number, String networkCountry) {
        if (number == null) return false;
        String n = number.replaceAll("[^0-9+]", "");
        if (n.startsWith("+346") || n.startsWith("00346")) return true;
        // National format: only meaningful on a Spanish network, where
        // mobile numbers are 9 digits long.
        return "es".equalsIgnoreCase(networkCountry) && n.matches("6[0-9]{8}");
    }
}
