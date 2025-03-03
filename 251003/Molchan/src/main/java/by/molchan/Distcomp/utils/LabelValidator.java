package by.molchan.Distcomp.utils;

import by.molchan.Distcomp.DTOs.Requests.ArticleRequestDTO;
import by.molchan.Distcomp.DTOs.Requests.LabelRequestDTO;
import by.molchan.Distcomp.services.LabelsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class LabelValidator implements Validator {
    private final LabelsService labelsService;

    @Autowired
    public LabelValidator(LabelsService labelsService) {
        this.labelsService = labelsService;
    }


    @Override
    public boolean supports(Class<?> clazz) {
        return ArticleRequestDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        if (!errors.hasFieldErrors()){
            LabelRequestDTO label = (LabelRequestDTO) target;
            if (labelsService.existsByName(label.getName())){
                errors.rejectValue("title", null, "Label with such name already exists");
            }
        }
    }
}
