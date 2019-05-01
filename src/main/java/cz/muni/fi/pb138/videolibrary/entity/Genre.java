package cz.muni.fi.pb138.videolibrary.entity;

public enum Genre {
    Action,
    Adventure,
    Animated,
    Biography,
    Comedy,
    Crime,
    Documentary,
    Drama,
    Family,
    Fantasy,
    Historical,
    Horror,
    Musical,
    Mystery,
    Political,
    Romance,
    ScienceFiction {
        @Override
        public String toString() {
            return "Science Fiction";
        }
    },
    Sport,
    Superhero,
    Thriller,
    War,
    Western
}
