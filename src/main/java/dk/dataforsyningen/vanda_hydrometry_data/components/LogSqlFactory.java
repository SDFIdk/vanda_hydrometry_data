package dk.dataforsyningen.vanda_hydrometry_data.components;

import org.jdbi.v3.core.statement.SqlLogger;
import org.jdbi.v3.core.statement.StatementContext;
import org.jdbi.v3.sqlobject.customizer.SqlStatementCustomizer;
import org.jdbi.v3.sqlobject.customizer.SqlStatementCustomizerFactory;
import org.jdbi.v3.sqlobject.customizer.SqlStatementCustomizingAnnotation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@SqlStatementCustomizingAnnotation(LogSqlFactory.Factory.class)
public @interface LogSqlFactory {

    class Factory implements SqlStatementCustomizerFactory {

        private Logger log = null;

        private static void logSql(Logger log, StatementContext context) {
            //System.out.println("Raw SQL:\n" + context.getRawSql());
            //System.out.println("Parsed SQL:\n" + context.getParsedSql().getSql());
            String sql = "" + context.getStatement();
            if (context.getStatement() == null) {
                sql = context.getRawSql();
            }
            log.debug("Statement SQL:\n" + sql);
        }

        @Override
        public SqlStatementCustomizer createForType(Annotation annotation, Class sqlObjectType) {

            log = LoggerFactory.getLogger(sqlObjectType);

            SqlLogger sqlLogger = new SqlLogger() {
                @Override
                public void logBeforeExecution(StatementContext context) {
                    logSql(log, context);
                }
                // @Override
                // public void logAfterExecution(StatementContext context) {
                // logSql(log, context);
                // }
            };
            return statement -> statement.setSqlLogger(sqlLogger);
        }
    }
}
