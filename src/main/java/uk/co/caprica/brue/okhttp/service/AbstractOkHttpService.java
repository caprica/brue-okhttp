/*
 * Copyright 2014 Caprica Software Limited
 * (http://www.capricasoftware.co.uk)
 *
 * This file is part of Brue.
 *
 * Brue is free software: you can redistribute it and/or modify it under the
 * terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option)
 * any later version.
 *
 * Brue is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public License for
 * more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Brue.  If not, see <http://www.gnu.org/licenses/>.
 */

package uk.co.caprica.brue.okhttp.service;

import com.squareup.okhttp.OkHttpClient;

public abstract class AbstractOkHttpService {

    /**
     * Shared HTTP Client.
     * <p>
     * Once created, this client is completely thread-safe and can safely be re-used.
     */
    protected static final OkHttpClient httpClient = new OkHttpClient();

    /**
     * Standard HTTP Accept header name.
     */
    protected static final String HEADER_ACCEPT = "Accept";

    /**
     * Standard media type for JSON requests and responses.
     */
    protected static final String MEDIA_TYPE_JSON = "application/json";

    /**
     *
     */
    protected static final String MEDIA_TYPE_XML = "text/xml";
}
