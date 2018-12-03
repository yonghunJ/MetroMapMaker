package mmm.data;

/**
 * This enum has the various possible states of the logo maker app
 * during the editing process which helps us determine which controls
 * are usable or not and what specific user actions should affect.
 * 
 * @author Richard McKenna
 * @author Yonghun Jeong
 * @version 1.0
 */
public enum MMMState {
    SELECTING_SHAPE,
    DRAGGING_SHAPE,
    STARTING_LINE,
    STARTING_STATION,
    STARTING_TEXT,
    STARTING_IMAGE,
    DRAGGING_NOTHING,
    DRAGGING_LINE,
    SIZING_NOTHING,
    STARTING_POLYLINE,
    POLYLINE_DWARING,
    DRAGGING_POlYLINE,
    DRAGGING_TEXT,
    DRAGGING_STATION
}
