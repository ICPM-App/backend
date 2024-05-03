package org.example.services.impl;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.dto.SessionDTO;
import org.example.dto.SessionHeaderDTO;
import org.example.entities.GalleryImage;
import org.example.entities.SessionContent;
import org.example.entities.SessionHeader;
import org.example.entities.User;
import org.example.repository.SessionContentRepository;
import org.example.repository.SessionHeaderRepository;
import org.example.repository.UserRepository;
import org.example.services.AgendaService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AgendaServiceImpl implements AgendaService {
    private final SessionHeaderRepository sessionHeaderRepository;
    private final SessionContentRepository sessionContentRepository;
    private final UserRepository userRepository;

    public List<SessionHeaderDTO> fetchAll() {
        List<SessionHeader> headers = sessionHeaderRepository.findAll();
        return headers.stream()
                .map(header -> new SessionHeaderDTO(
                        header.getId(),
                        header.getName(),
                        header.getHost(),
                        header.getLocation(),
                        header.getStartTime(),
                        header.getEndTime(),
                        header.getType()))
                .collect(Collectors.toList());
    }

    @Override
    public SessionDTO findById(Long id) {
        // Directly fetch the SessionHeader. Convert to SessionResponse inside the method.
        return sessionHeaderRepository.findById(id).map(header -> {
            String content = Optional.ofNullable(header.getContent()).map(SessionContent::getContent).orElse(null);
            return new SessionDTO(
                    header.getName(),
                    header.getHost(),
                    header.getLocation(),
                    header.getStartTime(),
                    header.getEndTime(),
                    header.getType(),
                    content
            );
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Session not found with id: " + id));
    }

    @Transactional
    public SessionHeader update(SessionDTO request, Long id) {
        SessionHeader header = sessionHeaderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Session not found"));

        header.setName(request.getName());
        header.setHost(request.getHost());
        header.setLocation(request.getLocation());
        header.setStartTime(request.getStartTime());
        header.setEndTime(request.getEndTime());

        SessionContent content = header.getContent();
        if (content == null) {
            content = new SessionContent();
            header.setContent(content);
        }
        content.setContent(request.getContent());
        //other content fields

        sessionContentRepository.save(content);
        return sessionHeaderRepository.save(header);
}

    @Transactional
    public SessionHeader create (SessionDTO request) {
        SessionHeader header = new SessionHeader();
        header.setName(request.getName());
        header.setHost(request.getHost());
        header.setLocation(request.getLocation());
        header.setStartTime(request.getStartTime());
        header.setEndTime(request.getEndTime());

        if (request.getContent() != null && !request.getContent().isEmpty()) {
            SessionContent content = new SessionContent();
            content.setContent(request.getContent());
            content.setHeader(header);
            header.setContent(content);
        }

        return sessionHeaderRepository.save(header);
    }

    @Transactional
    public void changeLikeStatusForSession(Boolean likes, String username, Long sessionId) {
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + username));

        SessionHeader session = sessionHeaderRepository.findById(sessionId)
                .orElseThrow(() -> new EntityNotFoundException("Session not found with id: " + sessionId));

        boolean isUserAlreadyLikes = user.getLikedBy().contains(session);

        if (likes && !isUserAlreadyLikes) {
            user.getLikedBy().add(session);
        } else if (!likes && isUserAlreadyLikes) {
            user.getLikedBy().remove(session);
        }

        userRepository.save(user); // Persist changes to the database
    }


    public List<SessionHeaderDTO> findLikedSessionsByUser(Integer userId){
        List<SessionHeader> likedSessions = sessionHeaderRepository.findByLikes_Id(userId);
        return likedSessions.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    public SessionHeaderDTO convertToDto(SessionHeader session) {
        return new SessionHeaderDTO(
                session.getId(),
                session.getName(),
                session.getHost(),
                session.getLocation(),
                session.getStartTime(),
                session.getEndTime(),
                session.getType()
        );
    }

    public List<Long> HeartedSessions (Integer userId) {
        return sessionHeaderRepository.findSessionIdsLikedByUser(userId);
    }

}
