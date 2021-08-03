package com.odak.meterreading.helper.query.builder;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.With;

/**
 * Instantiates query builder with provided configuration.
 *
 * @author ivano
 *
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@With(value = AccessLevel.PRIVATE)
public class QueryBuilderImpl implements QueryBuilder {

	@With(AccessLevel.PRIVATE)
	private final QueryConfiguration queryConfiguration;

	public QueryBuilderImpl() {
		this.queryConfiguration = QueryConfiguration.of();
	}

	@Override
	public QueryBuilder type(String type) {
		return this.withQueryConfiguration(queryConfiguration.withType(type));
	}

	@Override
	public QueryBuilder value(String value) {
		return this.withQueryConfiguration(queryConfiguration.withValue(value));
	}

	@Override
	public QueryBuilder limit(Integer limit) {
		return this.withQueryConfiguration(queryConfiguration.withLimit(limit));
	}

	@Override
	public QueryBuilder offset(Integer offset) {
		return this.withQueryConfiguration(queryConfiguration.withOffset(offset));
	}

	@Override
	public QueryBuilder sortBy(String sortBy) {
		return this.withQueryConfiguration(queryConfiguration.withSortBy(sortBy));
	}

	@Override
	public QueryBuilder sortDirection(String sortDirection) {
		return this.withQueryConfiguration(queryConfiguration.withSortDirection(sortDirection));
	}

	/**
	 * Configuration class containing available query configuration options.
	 *
	 * @author ivano
	 *
	 */
	@Data(staticConstructor = "of")
	@Getter(AccessLevel.NONE)
	@Setter(AccessLevel.NONE)
	public static class QueryConfiguration {

		private static final Integer DEFAULT_RECORDS_LIMIT = 5;
		private static final Integer DEFAULT_PAGE_OFFSET = 0;

		@With(AccessLevel.PRIVATE)
		public final String type;
		@With(AccessLevel.PRIVATE)
		public final String value;
		@With(AccessLevel.PRIVATE)
		public final Integer limit;
		@With(AccessLevel.PRIVATE)
		public final Integer offset;
		@With(AccessLevel.PRIVATE)
		public final String sortBy;
		@With(AccessLevel.PRIVATE)
		public final String sortDirection;

		/**
		 * Configures query options with provided query values, or keeps default values
		 * if there was no custom value provided.
		 *
		 * @return {@link QueryConfiguration} instance.
		 */
		public static QueryConfiguration of() {
			return QueryConfiguration.of("", "", DEFAULT_RECORDS_LIMIT, DEFAULT_PAGE_OFFSET, "", "");
		}
	}
}
