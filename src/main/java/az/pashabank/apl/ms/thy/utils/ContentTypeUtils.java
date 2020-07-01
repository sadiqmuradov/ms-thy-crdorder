package az.pashabank.apl.ms.thy.utils;

public class ContentTypeUtils {

    private static final String IMAGE_JPEG = ".jpg"; // image/jpeg
    private static final String IMAGE_PNG = ".png"; // image/png
    private static final String APPLICATION_PDF = ".pdf"; // application/pdf
    private static final String MS_WORD = ".doc"; // application/msword
    private static final String MS_WORDX = ".docx"; // application/vnd.openxmlformats-officedocument.wordprocessingml.document
    private static final String MS_EXCEL = ".xls"; // application/vnd.ms-excel
    private static final String MS_EXCELX = ".xlsx"; // application/vnd.openxmlformats-officedocument.spreadsheetml.sheet
    private static final String MS_POWERPOINT = ".ppt"; // application/vnd.ms-powerpoint
    private static final String MS_POWERPOINTX = ".pptx"; // application/vnd.openxmlformats-officedocument.presentationml.presentation
    private static final String TEXT_PLAIN = ".txt"; // text/plain

    private ContentTypeUtils() {
    }

    public static final String getExtension(String contentType) {
        String extension = "";

        switch (contentType) {
            case "image/jpeg":
                extension = IMAGE_JPEG;
                break;

            case "image/png":
            case "image/x-png":
                extension = IMAGE_PNG;
                break;

            case "application/pdf":
                extension = APPLICATION_PDF;
                break;

            case "application/msword":
                extension = MS_WORD;
                break;

            case "application/vnd.openxmlformats-officedocument.wordprocessingml.document":
                extension = MS_WORDX;
                break;

            case "application/vnd.ms-excel":
                extension = MS_EXCEL;
                break;

            case "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet":
                extension = MS_EXCELX;
                break;

            case "application/vnd.ms-powerpoint":
                extension = MS_POWERPOINT;
                break;

            case "application/vnd.openxmlformats-officedocument.presentationml.presentation":
                extension = MS_POWERPOINTX;
                break;

            case "text/plain":
                extension = TEXT_PLAIN;
                break;

            default:
        }

        return extension;
    }

}
