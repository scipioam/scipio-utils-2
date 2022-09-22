package com.github.scipioutils.core.io.stream;

import java.io.IOException;
import java.io.InputStream;
import java.util.zip.GZIPInputStream;

/**
 * @since 2022/9/21
 */
@FunctionalInterface
public interface InputStreamWrapper {

    InputStream wrap(InputStream origIn) throws IOException;

    InputStreamWrapper GZIP = GZIPInputStream::new;

}
