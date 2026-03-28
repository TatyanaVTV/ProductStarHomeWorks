package base.practical.third.html;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import static java.util.Objects.isNull;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
public class HTMLValidatorTest {
    private static final String VALID_HTML = "<div><p>Текст</p></div>";
    private static final String VALID_HTML_MIXED_CASE = "<Div><P>Текст</p></dIv>";
    private static final String VALID_BIG_HTML = "<html><body><div><span>Content</span></div></body></html>";
    private static final String SINGLE_TAG = "<p>Текст</p>";
    private static final String EMPTY_TAGS = "<div></div><span></span>";
    private static final String EMPTY_VALUE = "";
    private static final String TEXT_WITHOUT_TAGS = "Просто текст без тегов";
    private static final String WITH_COMMENTS = "<p><!-- comment --><div>Text</div></p>";

    private static final HTMLValidator VALIDATOR = new HTMLValidator();

    private static final String ERROR_MSG_FOR_VALID_CHECKS_RESULT =
            "Для корректного HTML должен вернуться положительный результат!";
    private static final String ERROR_MSG_FOR_VALID_CHECKS_MSG =
            "Для корректного HTML не должно быть сообщений об ошибке!";

    @Test
    public void testValidHtml() {
        var result = VALIDATOR.validate(VALID_HTML);
        logResult(result);

        assertTrue(result.isValid(), ERROR_MSG_FOR_VALID_CHECKS_RESULT);
        assertTrue(isNull(result.errorMessage()), ERROR_MSG_FOR_VALID_CHECKS_MSG);
    }

    @Test
    public void testValidHtml_UpperCase() {
        var result = VALIDATOR.validate(VALID_HTML.toUpperCase());
        logResult(result);

        assertTrue(result.isValid(), ERROR_MSG_FOR_VALID_CHECKS_RESULT);
        assertTrue(isNull(result.errorMessage()), ERROR_MSG_FOR_VALID_CHECKS_MSG);
    }

    @Test
    public void testValidHtml_MixedCase() {
        var result = VALIDATOR.validate(VALID_HTML_MIXED_CASE);
        logResult(result);

        assertTrue(result.isValid(), ERROR_MSG_FOR_VALID_CHECKS_RESULT);
        assertTrue(isNull(result.errorMessage()), ERROR_MSG_FOR_VALID_CHECKS_MSG);
    }

    @Test
    public void testValidHtml_ManyNestedTags() {
        var result = VALIDATOR.validate(VALID_BIG_HTML);
        logResult(result);

        assertTrue(result.isValid(), ERROR_MSG_FOR_VALID_CHECKS_RESULT);
        assertTrue(isNull(result.errorMessage()), ERROR_MSG_FOR_VALID_CHECKS_MSG);
    }

    @Test
    public void testValidHtml_SingleTag() {
        var result = VALIDATOR.validate(SINGLE_TAG);
        logResult(result);

        assertTrue(result.isValid(), ERROR_MSG_FOR_VALID_CHECKS_RESULT);
        assertTrue(isNull(result.errorMessage()), ERROR_MSG_FOR_VALID_CHECKS_MSG);
    }

    @Test
    public void testValidHtml_EmptyTags() {
        var result = VALIDATOR.validate(EMPTY_TAGS);
        logResult(result);

        assertTrue(result.isValid(), ERROR_MSG_FOR_VALID_CHECKS_RESULT);
        assertTrue(isNull(result.errorMessage()), ERROR_MSG_FOR_VALID_CHECKS_MSG);
    }

    @Test
    public void testValidHtml_EmptyString() {
        var result = VALIDATOR.validate(EMPTY_VALUE);
        logResult(result);

        assertTrue(result.isValid(), ERROR_MSG_FOR_VALID_CHECKS_RESULT); // Пустой html — валиден
        assertTrue(isNull(result.errorMessage()), ERROR_MSG_FOR_VALID_CHECKS_MSG);
    }

    @Test
    public void testValidHtml_OnlyTextNoTags() {
        var result = VALIDATOR.validate(TEXT_WITHOUT_TAGS);
        logResult(result);

        assertTrue(result.isValid(), ERROR_MSG_FOR_VALID_CHECKS_RESULT); // Текст без тегов — валиден
        assertTrue(isNull(result.errorMessage()), ERROR_MSG_FOR_VALID_CHECKS_MSG);
    }

    @Test
    public void testValidHtml_SelfClosingTags() {
        var result = VALIDATOR.validate("<br><img>");
        logResult(result);

        assertTrue(result.isValid(), ERROR_MSG_FOR_VALID_CHECKS_RESULT);
        assertTrue(isNull(result.errorMessage()), ERROR_MSG_FOR_VALID_CHECKS_MSG);
    }

    @Test
    public void testValidHtml_Comments() {
        var result = VALIDATOR.validate(WITH_COMMENTS);
        logResult(result);

        assertTrue(result.isValid(), ERROR_MSG_FOR_VALID_CHECKS_RESULT);
        assertTrue(isNull(result.errorMessage()), ERROR_MSG_FOR_VALID_CHECKS_MSG);
    }

    @Test
    public void testInvalidHtml_InvalidOrder() {
        var invalidOrder = "<div><p>Текст</div></p>";
        var result = VALIDATOR.validate(invalidOrder);
        logResult(result);

        assertFalse(result.isValid());
        assertTrue(result.errorMessage().contains("Несоответствие тэгов: ожидался </p>, получен </div>"));
    }

    @Test
    public void testInvalidHtml_ComplexInvalidOrder() {
        var result = VALIDATOR.validate("<section><div><p>Text</p><span>More</section></span>");
        logResult(result);

        assertFalse(result.isValid());
        assertTrue(result.errorMessage().contains("Несоответствие тэгов: ожидался </span>, получен </section>"));
    }

    @Test
    public void testInvalidHtml_UnclosedTag() {
        var notClosedTags = "<div><p>Hello";
        var result = VALIDATOR.validate(notClosedTags);
        logResult(result);

        assertFalse(result.isValid());
        assertTrue(result.errorMessage().contains("Незакрытые теги: [div, p]")); // оба тэга попали в стэк
    }

    @Test
    public void testInvalidHtml_MultipleUnclosedTags() {
        var result = VALIDATOR.validate("<div><span><p>Content");
        logResult(result);

        assertFalse(result.isValid());
        // накопление в стэке без промежуточных закрытий
        assertTrue(result.errorMessage().contains("Незакрытые теги: [div, span, p]"));
    }

    @Test
    public void testInvalidHtml_OnlyClosingTag() {
        var result = VALIDATOR.validate("</div>");
        logResult(result);

        assertFalse(result.isValid());
        assertTrue(result.errorMessage().contains("Закрывающий тег без открывающего (</div>)"));
    }

    @Test
    public void testInvalidHtml_ExtraClosingTag() {
        var result = VALIDATOR.validate("<div>Text</div></p>");
        logResult(result);

        assertFalse(result.isValid());
        assertTrue(result.errorMessage().contains("Закрывающий тег без открывающего (</p>)"));
    }

    private void logResult(ValidationResult result) {
        log.info("Validation result: {}", result);
    }
}
