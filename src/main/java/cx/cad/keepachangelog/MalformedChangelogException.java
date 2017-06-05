/**
 * This file is a part of changelog-parser and is licensed as detailed in LICENSE.md.
 */
package cx.cad.keepachangelog;

public class MalformedChangelogException extends RuntimeException {
    public MalformedChangelogException(String message, Throwable e) {
        super(message, e);
    }
    public MalformedChangelogException(Throwable e) {
        super(e);
    }
}
