package cuke4duke.internal.language;

import org.jruby.RubyArray;

import cuke4duke.internal.jvmclass.ArgumentsConverter;


public abstract class AbstractStepDefinition implements StepDefinition {
    private final ArgumentsConverter argumentsConverter;
    private final AbstractProgrammingLanguage programmingLanguage;

    public AbstractStepDefinition(AbstractProgrammingLanguage programmingLanguage) {
        this.programmingLanguage = programmingLanguage;
        this.argumentsConverter = new ArgumentsConverter(programmingLanguage.getTransforms());
    }

    protected void register() throws Throwable {
        programmingLanguage.availableStepDefinition(regexp_source(), file_colon_line());
    }

    public final void invoke(RubyArray rubyArgs) throws Throwable {
        programmingLanguage.invokedStepDefinition(regexp_source(), file_colon_line());
        Object[] args = rubyArgs.toArray();
        Object[] javaArgs = argumentsConverter.convert(getParameterTypes(args), args);
        invokeWithJavaArgs(javaArgs);
    }

    protected abstract Class<?>[] getParameterTypes(Object[] args);

    public abstract void invokeWithJavaArgs(Object[] args) throws Throwable;
}
