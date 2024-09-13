package com.ims.service.impl;

import com.ims.dto.*;
import com.ims.entity.*;
import com.ims.exception.BusinessException;
import com.ims.repository.ContractRepository;
import com.ims.repository.LoanApplicationRepository;
import com.ims.repository.LoanOfferRepository;
import com.ims.repository.UserRepository;
import com.ims.service.ImsService;
import com.ims.service.LoanApplicationService;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.SneakyThrows;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class LoanApplicationServiceImpl implements LoanApplicationService{

    @Autowired
    private LoanApplicationRepository loanApplicationRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ContractRepository contractRepository;
    @Autowired
    private LoanOfferRepository loanOfferRepository;

    public String applyLoan(LoanApplicationRequestDTO requestDTO){

        LoanApplication loanApplication = new LoanApplication();
        BeanUtils.copyProperties(requestDTO, loanApplication);
        UserEntity lender = userRepository.findById(requestDTO.getLenderId()).get();
        Random random = new Random();
        int randomNum = random.nextInt((10 - 1) + 1) + 1;
        loanApplication.setLender(lender);
        loanApplication.setLoanOfferId(requestDTO.getLoanOfferId());
        loanApplication.setRiskScore(Double.valueOf(randomNum));
        loanApplication.setELoanStatusLender(EStatus.INITIATED);
        loanApplication.setELoanStatusMerchant(EStatus.INITIATED);
        UserEntity merchant = userRepository.findById(requestDTO.getMerchantId()).get();
        loanApplication.setMerchant(merchant);
        loanApplication.setCurrency(ECurrency.valueOf(requestDTO.getCurrency()));
        loanApplication.setCreatedDateTime(LocalDateTime.now());
        loanApplication.setUpdatedDateTime(LocalDateTime.now());
        loanApplication = loanApplicationRepository.save(loanApplication);

        return "Loan applied successfully, reference Id is: "+loanApplication.getId();
    }

    public LoanApplicationResponseDTO getLoanDetails(Long loanApplicationId){
       LoanApplication le = loanApplicationRepository.findById(loanApplicationId).get();
       LoanApplicationResponseDTO responseDTO = new LoanApplicationResponseDTO();
       BeanUtils.copyProperties(le, responseDTO);
       return responseDTO;
    }

    public List<LoanApplicationResponseDTO> getAllLoanApplicationForLender(Long lenderId){
        List<LoanApplication> loanApplicationList = loanApplicationRepository.findAllByLenderId(lenderId);
        List<LoanApplicationResponseDTO> dtos = new ArrayList<>();
        LoanApplicationResponseDTO responseDTO = null;
        for(LoanApplication la: loanApplicationList){
            responseDTO = new LoanApplicationResponseDTO();
            BeanUtils.copyProperties(la, responseDTO);
            responseDTO.setLender(la.getLender());
            responseDTO.setLoanOfferId(la.getLoanOfferId());
            responseDTO.setMerchant(la.getMerchant());
            responseDTO.setELoanStatusLender(la.getELoanStatusLender().toString());
            responseDTO.setELoanStatusMerchant(la.getELoanStatusMerchant().toString());
            responseDTO.setCreatedDateTime(la.getCreatedDateTime());
            responseDTO.setUpdatedDateTime(la.getUpdatedDateTime());
            responseDTO.setCurrency(la.getCurrency().toString());
            dtos.add(responseDTO);
        }
        return dtos;
    }
    public LoanApplicationResponseDTO update(LoanApplicationRequestDTO input, Long id) {
        LoanApplication pe = loanApplicationRepository.findById(id)
                .orElseThrow(() -> new BusinessException(List.of(new ErrorDTO("NOT_FOUND", "Cannot find Loan application with Id: "+id))));
        BeanUtils.copyProperties(input, pe);
        pe.setUpdatedDateTime(LocalDateTime.now());
        pe.setId(id);
        pe.setLoanAmountRequested(input.getLoanAmountRequested());
        pe = loanApplicationRepository.save(pe);
        LoanApplicationResponseDTO responseDTO = new LoanApplicationResponseDTO();
            BeanUtils.copyProperties(pe, responseDTO);
        return responseDTO;
    }

    public LoanApplicationResponseDTO delete(Long id) {

        LoanApplication pe = loanApplicationRepository.findById(id)
                .orElseThrow(() -> new BusinessException(List.of(new ErrorDTO("NOT_FOUND", "Cannot find Loan with Id: "+id))));
        LoanApplicationResponseDTO loanApplicationResponseDTO = new LoanApplicationResponseDTO();
        BeanUtils.copyProperties(pe, loanApplicationResponseDTO);
        loanApplicationRepository.deleteById(id);
        return loanApplicationResponseDTO;
    }
    public List<LoanApplicationResponseDTO> search(Long id) {
        List<LoanApplication> loanApplications = loanApplicationRepository.findAllByLenderId(id);
        LoanApplicationResponseDTO loanApplicationResponseDTO =  null;
        List<LoanApplicationResponseDTO> dtos = new ArrayList<>();
        for(LoanApplication loanApplication : loanApplications){
            loanApplicationResponseDTO = new LoanApplicationResponseDTO();
            BeanUtils.copyProperties(loanApplication, loanApplicationResponseDTO);;
            loanApplicationResponseDTO.setId(loanApplication.getId());
            dtos.add(loanApplicationResponseDTO);
        }
        return dtos;
    }
    public LoanApplicationResponseDTO updateLoanAmount(Double loanAmount, Long id) {
        LoanApplication pe = loanApplicationRepository.findById(id)
                .orElseThrow(() -> new BusinessException(List.of(new ErrorDTO("NOT_FOUND", "Cannot find Loan application with Id: "+id))));
        pe.setUpdatedDateTime(LocalDateTime.now());
        pe.setId(id);
        pe.setLoanAmountRequested(loanAmount);
        pe = loanApplicationRepository.save(pe);
        LoanApplicationResponseDTO responseDTO = new LoanApplicationResponseDTO();
        BeanUtils.copyProperties(pe, responseDTO);
        return responseDTO;
    }

    public LoanApplicationResponseDTO updateLoanStatus(LoanStatusDTO loanStatusDTO) {
        LoanApplication pe = loanApplicationRepository.findById(loanStatusDTO.getId())
                .orElseThrow(() -> new BusinessException(List.of(new ErrorDTO("NOT_FOUND", "Cannot find Loan application with Id: "+loanStatusDTO.getId()))));
        pe.setUpdatedDateTime(LocalDateTime.now());
        pe.setId(loanStatusDTO.getId());
        if("ROLE_MERCHANT".equalsIgnoreCase(loanStatusDTO.getRole())){
            pe.setELoanStatusMerchant(EStatus.valueOf(loanStatusDTO.getLoanStatus()));
        }else{
            LoanOffers loanOffers=loanOfferRepository.findById(loanStatusDTO.getLoanOfferId()).get();

            ContractEntity contractEntity = ContractEntity.builder().approvedLoanAmount(pe.getLoanAmountRequested())
                    .loanApplication(pe).repaymentCriteria(loanOffers.getLoanCriteria())
                    .interestRate(loanOffers.getMaxInterestRate())
                    .build();
            try{
                createPdf(contractEntity);
            }
            catch(Exception e)
            {

            }
            contractRepository.save(contractEntity);
            pe.setELoanStatusLender(EStatus.APPROVED);
        }
        pe = loanApplicationRepository.save(pe);
        LoanApplicationResponseDTO responseDTO = new LoanApplicationResponseDTO();
        BeanUtils.copyProperties(pe, responseDTO);
        if("ROLE_MERCHANT".equalsIgnoreCase(loanStatusDTO.getRole())){
            responseDTO.setELoanStatusMerchant(loanStatusDTO.getLoanStatus());
        }else{
            if(loanStatusDTO.getLoanStatus().equalsIgnoreCase("APPROVED"))
            {
                responseDTO.setELoanStatusMerchant(loanStatusDTO.getLoanStatus());

            }
            responseDTO.setELoanStatusLender(loanStatusDTO.getLoanStatus());
        }
        return responseDTO;
    }

    @SneakyThrows
    private void createPdf(ContractEntity contract)  {

        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream("contract.pdf"));

        document.open();

        PdfPTable table = new PdfPTable(3);
        addTableHeader(table);
        addRows(table, contract);
        addCustomRows(table);

        document.add(table);
        try {
            Path filePath = Path.of("contract.pdf");
            byte[] fileContent = Files.readAllBytes(filePath);
            StringBuilder sb = new StringBuilder();
           for(byte b : fileContent)
           {
                sb.append(b);
           }
           contract.setDocument(sb.toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        document.close();
    }
    private void addTableHeader(PdfPTable table) {
        Stream.of("Loan amount (EUR)", "Interest rate (%)", "Loan remark")
                .forEach(columnTitle -> {
                    PdfPCell header = new PdfPCell();
                    header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    header.setBorderWidth(1);
                    header.setPhrase(new Phrase(columnTitle));
                    table.addCell(header);
                });
    }
    private void addRows(PdfPTable table,ContractEntity contractEntity) {
        table.addCell(contractEntity.getApprovedLoanAmount().toString() );
        table.addCell(contractEntity.getInterestRate().toString());
        table.addCell(contractEntity.getRepaymentCriteria());
    }
    private void addCustomRows(PdfPTable table)
            throws URISyntaxException, BadElementException, IOException {
        Path path = Paths.get(ClassLoader.getSystemResource("contract.jpg").toURI());
        Image img = Image.getInstance(path.toAbsolutePath().toString());
        img.scalePercent(10);

        PdfPCell imageCell = new PdfPCell(img);
        table.addCell(imageCell);

    }

}
