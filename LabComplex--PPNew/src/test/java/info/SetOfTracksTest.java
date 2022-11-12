package info;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import org.springframework.test.util.ReflectionTestUtils;
import track.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SetOfTracksTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    @Mock
    List<MusicTrack> mockTrackList;

    @Mock
    List<Integer> mockLastDeletedTracks;

    @Mock
    MusicTrack mockTrack;

    @Spy
    @InjectMocks
    SetOfTracks spySetOfTracks;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ReflectionTestUtils.setField(spySetOfTracks, "trackList", mockTrackList);
        System.setOut(new PrintStream(outContent));

    }

    @AfterEach
    public void restore() {
        System.setOut(originalOut);
    }

    @Test
    void testAddNewTrackNotCorrectGenre() {
        assertNull(spySetOfTracks.addNewTrack(new String[]{"name=testName", "genre=temp", "duration=00:00:00"}));
    }

    @Test
    void testAddNewTrackDuplicate() {
        doReturn(true).when(spySetOfTracks).checkForDuplicateByName("testName");
        assertNull(spySetOfTracks.addNewTrack(new String[]{"name=testName", "genre=Pop", "duration=00:00:00"}));
    }

    @Test
    void testAddNewTrackNotCorrectParams() {
        doReturn(false).when(spySetOfTracks).checkForDuplicateByName("testName");
        assertNull(spySetOfTracks.addNewTrack(new String[]{"name=testName", "genre=Pop"}));
    }

    @Test
    void testAddNewTrackPopGenre() {
        when(spySetOfTracks.checkForDuplicateByName("testName")).thenReturn(false);
        assertEquals(PopMusic.class, spySetOfTracks
                .addNewTrack(new String[]{"name=testName", "genre=Pop", "duration=00:00:00"}).getClass());
    }

    @Test
    void testAddNewTrackRockGenre() {
        when(spySetOfTracks.checkForDuplicateByName("testName")).thenReturn(false);
        assertEquals(RockMusic.class, spySetOfTracks
                .addNewTrack(new String[]{"name=testName", "genre=Rock", "duration=00:00:00"}).getClass());
    }

    @Test
    void testAddNewTrackElectronicGenre() {
        when(spySetOfTracks.checkForDuplicateByName("testName")).thenReturn(false);
        assertEquals(ElectronicMusic.class, spySetOfTracks
                .addNewTrack(new String[]{"name=testName", "genre=Electronic", "duration=00:00:00"}).getClass());
    }

    @Test
    void addNewTrackCountryGenre() {
        when(spySetOfTracks.checkForDuplicateByName("testName")).thenReturn(false);
        assertEquals(CountryMusic.class, spySetOfTracks
                .addNewTrack(new String[]{"name=testName", "genre=Country", "duration=00:00:00"}).getClass());
    }

    @Test
    void testAddNewTrackClassicalGenre() {
        when(spySetOfTracks.checkForDuplicateByName("testName")).thenReturn(false);
        assertEquals(ClassicalMusic.class, spySetOfTracks
                .addNewTrack(new String[]{"name=testName", "genre=Classical", "duration=00:00:00"}).getClass());
    }

    @Test
    void testAddNewAlbumCorrect() {
        Album mockAlbum = mock(Album.class);
        SetOfTracks newMockSet = mock(SetOfTracks.class);
        when(mockAlbum.getSet()).thenReturn(newMockSet);
        when(newMockSet.getTrackList()).thenReturn(new ArrayList<>() {{ add(mockTrack);}});
        spySetOfTracks.addNewAlbum(mockAlbum);
        verify(mockTrackList).add(mockTrack);
    }

    @Test
    void testCheckForDuplicateByNameTrue() {
        MusicTrack trackToTest = new MusicTrack();
        trackToTest.builder(new HashMap<>(){{ put("name", "testName"); put("duration", "00:00:01");}});
        ReflectionTestUtils.setField(spySetOfTracks, "trackList", new ArrayList<>(){{ add(trackToTest); }});
        assertTrue(spySetOfTracks.checkForDuplicateByName("testName"));
        assertEquals("Track already exist on disk\r\n",outContent.toString());
    }

    @Test
    void testCheckForDuplicateByNameFalse() {
        MusicTrack trackToTest = new MusicTrack();
        trackToTest.builder(new HashMap<>(){{ put("name", "testName"); put("duration", "00:00:01");}});
        ReflectionTestUtils.setField(spySetOfTracks, "trackList", new ArrayList<>(){{ add(trackToTest); }});
        assertFalse(spySetOfTracks.checkForDuplicateByName("testName2"));
    }

    @Test
    void testDeleteTrackByNameDiskEmpty() {
        MusicTrack trackToTest = new MusicTrack();
        trackToTest.builder(new HashMap<>(){{ put("name", "testName"); put("duration", "00:00:01");}});
        ReflectionTestUtils.setField(spySetOfTracks, "trackList", new ArrayList<>(){{ add(trackToTest); }});
        when(spySetOfTracks.setIsEmpty()).thenReturn(true);
        assertFalse(spySetOfTracks.deleteTrackByName(trackToTest.getName()));
    }

    @Test
    void testDeleteTrackByNameTrackExist() {
        MusicTrack trackToTest = new MusicTrack();
        trackToTest.builder(new HashMap<>(){{ put("name", "testName"); put("duration", "00:00:01");}});
        ReflectionTestUtils.setField(spySetOfTracks, "trackList", new ArrayList<>(){{ add(trackToTest); }});
        when(spySetOfTracks.setIsEmpty()).thenReturn(false);
        assertTrue(spySetOfTracks.deleteTrackByName(trackToTest.getName()));
    }

    @Test
    void testDeleteTrackByNameTrackNotExist() {
        MusicTrack trackToTest = new MusicTrack();
        trackToTest.builder(new HashMap<>(){{ put("name", "testName"); put("duration", "00:00:01");}});
        ReflectionTestUtils.setField(spySetOfTracks, "trackList", new ArrayList<>(){{ add(trackToTest); }});
        when(spySetOfTracks.setIsEmpty()).thenReturn(false);
        assertFalse(spySetOfTracks.deleteTrackByName("testName2"));
    }

    @Test
    void testDeleteTracksByAlbumNameDiskEmpty() {
        MusicTrack trackToTest = new MusicTrack();
        trackToTest.builder(new HashMap<>(){{
            put("name", "testName"); put("duration", "00:00:01"); put("album", "testAlbumName");}});
        ReflectionTestUtils.setField(spySetOfTracks, "trackList", new ArrayList<>(){{ add(trackToTest); }});
        when(spySetOfTracks.setIsEmpty()).thenReturn(true);
        assertEquals(1, spySetOfTracks.deleteTracksByAlbumName("testAlbumName"));
    }

    @Test
    void testDeleteTracksByAlbumNameCorrect() {
        ReflectionTestUtils.setField(spySetOfTracks, "trackList", new ArrayList<>(){{ add(mockTrack); }});
        when(spySetOfTracks.setIsEmpty()).thenReturn(false);
        when(mockTrack.getAlbumName()).thenReturn("testAlbumName");
        doNothing().when(mockTrack).setDeleteStatus(true);
        assertEquals(1, spySetOfTracks.deleteTracksByAlbumName("testAlbumName"));
    }

    @Test
    void testRestoreLastTracks() {
        ReflectionTestUtils.setField(spySetOfTracks, "lastDeletedTracks", mockLastDeletedTracks);
        spySetOfTracks.restoreLastTracks();
        verify(mockLastDeletedTracks).forEach(any());
        verify(mockLastDeletedTracks).clear();
    }

    @Test
    void testRestorePreviousParamsForTrack(){
        Map<String, String> mockLastModifiedTrackParams = mock(HashMap.class);
        ReflectionTestUtils.setField(spySetOfTracks, "trackList", new ArrayList<>(){{ add(mockTrack); }});
        ReflectionTestUtils.setField(spySetOfTracks, "lastModifiedTrackParams", mockLastModifiedTrackParams);
        when(mockTrack.getName()).thenReturn("testName");
        when(mockLastModifiedTrackParams.get("name")).thenReturn("testName");
        spySetOfTracks.restorePreviousParamsForTrack();
        verify(mockTrack).builder(mockLastModifiedTrackParams);

    }

    @Test
    void testChangeTrackParamsDiskEmpty() {
        when(spySetOfTracks.setIsEmpty()).thenReturn(true);
        assertFalse(spySetOfTracks.changeTrackParams(new String[]{}));
    }

    @Test
    void testChangeTrackParamsTrackNotExist() {
        MusicTrack trackToTest = new MusicTrack();
        trackToTest.builder(new HashMap<>(){{ put("name", "testName"); put("duration", "00:00:01");}});
        ReflectionTestUtils.setField(spySetOfTracks, "trackList", new ArrayList<>(){{ add(trackToTest); }});
        when(spySetOfTracks.setIsEmpty()).thenReturn(false);
        assertFalse(spySetOfTracks
                .changeTrackParams(new String[]{ "name=testName2", "author=newName"}));
    }

    @Test
    void testChangeTrackParamsTrack() {
        MusicTrack trackToTest = new MusicTrack();
        trackToTest.builder(new HashMap<>(){{ put("name", "testName"); put("duration", "00:00:01");}});
        ReflectionTestUtils.setField(spySetOfTracks, "trackList", new ArrayList<>(){{ add(trackToTest); }});
        when(spySetOfTracks.setIsEmpty()).thenReturn(false);
        assertTrue(spySetOfTracks
                .changeTrackParams(new String[]{ "name=testName", "author=newName"}));
    }

    @Test
    void testPrintTracksBySomethingDiskEmpty() {
        when(spySetOfTracks.setIsEmpty()).thenReturn(true);
        assertFalse(spySetOfTracks.printTracksBy(new String[]{"name", "testName"}));
        assertEquals("", outContent.toString());
    }

    @Test
    void testPrintTracksBySomethingNotCorrectParam() {
        when(spySetOfTracks.setIsEmpty()).thenReturn(false);
        assertFalse(spySetOfTracks.printTracksBy(new String[]{"name"}));
        assertEquals("", outContent.toString());
    }

    @Test
    void testPrintTracksByNameTrackExist() {
        ReflectionTestUtils.setField(spySetOfTracks, "trackList", new ArrayList<>(){{ add(mockTrack); }});
        when(spySetOfTracks.setIsEmpty()).thenReturn(false);
        when(mockTrack.getName()).thenReturn("testName");
        when(mockTrack.isDeleteStatus()).thenReturn(false);
        when(mockTrack.toString()).thenReturn("test");
        assertTrue(spySetOfTracks.printTracksBy(new String[]{"name", "testName"}));
        assertEquals("test\r\n", outContent.toString());
    }

    @Test
    void testPrintTracksByAuthorTrackExist() {
        ReflectionTestUtils.setField(spySetOfTracks, "trackList", new ArrayList<>(){{ add(mockTrack); }});
        when(spySetOfTracks.setIsEmpty()).thenReturn(false);
        when(mockTrack.getAuthor()).thenReturn("testName");
        when(mockTrack.isDeleteStatus()).thenReturn(false);
        when(mockTrack.toString()).thenReturn("test");
        assertTrue(spySetOfTracks.printTracksBy(new String[]{"author", "testName"}));
        assertEquals("test\r\n", outContent.toString());
    }

    @Test
    void testPrintTracksByDateOfWritingTrackExist() {
        ReflectionTestUtils.setField(spySetOfTracks, "trackList", new ArrayList<>(){{ add(mockTrack); }});
        when(spySetOfTracks.setIsEmpty()).thenReturn(false);
        when(mockTrack.getDateOfWriting()).thenReturn("1900-01-01");
        when(mockTrack.isDeleteStatus()).thenReturn(false);
        when(mockTrack.toString()).thenReturn("test");
        assertTrue(spySetOfTracks.printTracksBy(new String[]{"dateOfWriting", "1900-01-01"}));
        assertEquals("test\r\n", outContent.toString());
    }

    @Test
    void testPrintTracksByDateOfPublicationTrackExist() {
        ReflectionTestUtils.setField(spySetOfTracks, "trackList", new ArrayList<>(){{ add(mockTrack); }});
        when(spySetOfTracks.setIsEmpty()).thenReturn(false);
        when(mockTrack.getDateOfPublication()).thenReturn("1900-01-01");
        when(mockTrack.isDeleteStatus()).thenReturn(false);
        when(mockTrack.toString()).thenReturn("test");
        assertTrue(spySetOfTracks.printTracksBy(new String[]{"dateOfPublication", "1900-01-01"}));
        assertEquals("test\r\n", outContent.toString());
    }

    @Test
    void testPrintTracksByGenreTrackExist() {
        ReflectionTestUtils.setField(spySetOfTracks, "trackList", new ArrayList<>(){{ add(mockTrack); }});
        when(spySetOfTracks.setIsEmpty()).thenReturn(false);
        when(mockTrack.getGenre()).thenReturn("testGenre");
        when(mockTrack.isDeleteStatus()).thenReturn(false);
        when(mockTrack.toString()).thenReturn("test");
        assertTrue(spySetOfTracks.printTracksBy(new String[]{"genre", "testGenre"}));
        assertEquals("test\r\n", outContent.toString());
    }

    @Test
    void testPrintTracksByAlbumTrackExist() {
        ReflectionTestUtils.setField(spySetOfTracks, "trackList", new ArrayList<>(){{ add(mockTrack); }});
        when(spySetOfTracks.setIsEmpty()).thenReturn(false);
        when(mockTrack.getAlbumName()).thenReturn("testAlbum");
        when(mockTrack.isDeleteStatus()).thenReturn(false);
        when(mockTrack.toString()).thenReturn("test");
        assertTrue(spySetOfTracks.printTracksBy(new String[]{"album", "testAlbum"}));
        assertEquals("test\r\n", outContent.toString());
    }

    @Test
    void testPrintTracksBySomethingTrackNotExist() {
        ReflectionTestUtils.setField(spySetOfTracks, "trackList", new ArrayList<>(){{ add(mockTrack); }});
        when(spySetOfTracks.setIsEmpty()).thenReturn(false);
        when(mockTrack.getName()).thenReturn("testName2");
        assertFalse(spySetOfTracks.printTracksBy(new String[]{"name", "testName"}));
        verify(mockTrack, times(0)).isDeleteStatus();
        assertEquals("There are no tracks with this parameter on the disc\r\n", outContent.toString());
    }

    @Test
    void testPrintTracksByDurationBordersNotCorrectParams() {
        when(spySetOfTracks.setIsEmpty()).thenReturn(false);
        assertFalse(spySetOfTracks
                .printTracksByDurationBorders(new String[]{"1", "2", "3", "4", "5"}));
        assertEquals("", outContent.toString());
    }

    @Test
    void testPrintTracksByDurationBordersDiskEmpty() {
        when(spySetOfTracks.setIsEmpty()).thenReturn(true);
        assertFalse(spySetOfTracks
                .printTracksByDurationBorders(new String[]{"find", "duration", "<00:02:00", ">00:01:00"}));
        assertEquals("", outContent.toString());
    }

    @Test
    void testPrintTracksByDurationBordersNotCorrectFormatForLess() {
        ReflectionTestUtils.setField(spySetOfTracks, "trackList", new ArrayList<>(){{ add(mockTrack); }});
        when(spySetOfTracks.setIsEmpty()).thenReturn(false);
        when(mockTrack.getDuration()).thenReturn("00:01:30");
        when(mockTrack.isDeleteStatus()).thenReturn(false);
        when(mockTrack.toString()).thenReturn("test");
        assertFalse(spySetOfTracks
                .printTracksByDurationBorders(new String[]{"find", "duration", "<0002:00"}));
        assertEquals("", outContent.toString());
    }

    @Test
    void testPrintTracksByDurationBordersNotCorrectFormatForMore() {
        ReflectionTestUtils.setField(spySetOfTracks, "trackList", new ArrayList<>(){{ add(mockTrack); }});
        when(spySetOfTracks.setIsEmpty()).thenReturn(false);
        when(mockTrack.getDuration()).thenReturn("00:01:30");
        when(mockTrack.isDeleteStatus()).thenReturn(false);
        when(mockTrack.toString()).thenReturn("test");
        assertFalse(spySetOfTracks
                .printTracksByDurationBorders(new String[]{"find", "duration", ">0001:00"}));
        assertEquals("", outContent.toString());
    }

    @Test
    void testPrintTracksByDurationBordersCorrectTwoBordersAndTrackExist() {
        ReflectionTestUtils.setField(spySetOfTracks, "trackList", new ArrayList<>(){{ add(mockTrack); }});
        when(spySetOfTracks.setIsEmpty()).thenReturn(false);
        when(mockTrack.getDuration()).thenReturn("00:01:30");
        when(mockTrack.isDeleteStatus()).thenReturn(false);
        when(mockTrack.toString()).thenReturn("test");
        assertTrue(spySetOfTracks
                .printTracksByDurationBorders(new String[]{"find", "duration", "<00:02:00", ">00:01:00"}));
        assertEquals("test\r\n", outContent.toString());
    }

    @Test
    void testPrintTracksByDurationBordersCorrectTwoBordersAndTrackExistButNotIn() {
        ReflectionTestUtils.setField(spySetOfTracks, "trackList", new ArrayList<>(){{ add(mockTrack); }});
        when(spySetOfTracks.setIsEmpty()).thenReturn(false);
        when(mockTrack.getDuration()).thenReturn("00:03:30");
        when(mockTrack.isDeleteStatus()).thenReturn(false);
        when(mockTrack.toString()).thenReturn("test");
        assertTrue(spySetOfTracks
                .printTracksByDurationBorders(new String[]{"find", "duration", "<00:02:00", ">00:01:00"}));
        assertEquals("", outContent.toString());
    }

    @Test
    void testPrintTracksByDurationBordersCorrectOneLessAndTrackExist() {
        ReflectionTestUtils.setField(spySetOfTracks, "trackList", new ArrayList<>(){{ add(mockTrack); }});
        when(spySetOfTracks.setIsEmpty()).thenReturn(false);
        when(mockTrack.getDuration()).thenReturn("00:01:30");
        when(mockTrack.isDeleteStatus()).thenReturn(false);
        when(mockTrack.toString()).thenReturn("test");
        assertTrue(spySetOfTracks
                .printTracksByDurationBorders(new String[]{"find", "duration", ">00:01:00"}));
        assertEquals("test\r\n", outContent.toString());
    }

    @Test
    void testPrintTracksByDurationBordersCorrectOneMoreAndTrackExist() {
        ReflectionTestUtils.setField(spySetOfTracks, "trackList", new ArrayList<>(){{ add(mockTrack); }});
        when(spySetOfTracks.setIsEmpty()).thenReturn(false);
        when(mockTrack.getDuration()).thenReturn("00:01:30");
        when(mockTrack.isDeleteStatus()).thenReturn(false);
        when(mockTrack.toString()).thenReturn("test");
        assertTrue(spySetOfTracks
                .printTracksByDurationBorders(new String[]{"find", "duration", "<00:02:00"}));
        assertEquals("test\r\n", outContent.toString());
    }

    @Test
    void testPrintTracksByDurationBordersCorrectTrackDeleted() {
        ReflectionTestUtils.setField(spySetOfTracks, "trackList", new ArrayList<>(){{ add(mockTrack); }});
        when(spySetOfTracks.setIsEmpty()).thenReturn(false);
        when(mockTrack.getDuration()).thenReturn("00:01:30");
        when(mockTrack.isDeleteStatus()).thenReturn(true);
        when(mockTrack.toString()).thenReturn("test");
        assertTrue(spySetOfTracks
                .printTracksByDurationBorders(new String[]{"find", "duration", "<00:02:00"}));
        assertEquals("", outContent.toString());
    }

    @Test
    void testSortListByName() {
        spySetOfTracks.sortListBy("name");
        verify(mockTrackList).sort(any());
    }

    @Test
    void testSortListByGenre() {
        spySetOfTracks.sortListBy("genre");
        verify(mockTrackList).sort(any());
    }

    @Test
    void testSortListByDuration() {
        spySetOfTracks.sortListBy("duration");
        verify(mockTrackList).sort(any());
    }

    @Test
    void testSortListByNotCorrectParam() {
        spySetOfTracks.sortListBy("author");
        verify(mockTrackList, times(0)).sort(any());
    }

    @Test
    void testPrintDurationForAllTracksDiskEmpty() {
        when(spySetOfTracks.setIsEmpty()).thenReturn(true);
        spySetOfTracks.printDurationForAllTracks();
        assertEquals("", outContent.toString());
    }

    @Test
    void testPrintDurationForAllTracksTrackDeleted() {
        ReflectionTestUtils.setField(spySetOfTracks, "trackList", new ArrayList<>(){{ add(mockTrack); }});
        when(spySetOfTracks.setIsEmpty()).thenReturn(false);
        when(mockTrack.isDeleteStatus()).thenReturn(true);
        spySetOfTracks.printDurationForAllTracks();
        assertEquals("Duration of all tracks together: 0:0:0\r\n", outContent.toString());
    }

    @Test
    void testPrintDurationForAllTracksOneTrackExist() {
        ReflectionTestUtils.setField(spySetOfTracks, "trackList", new ArrayList<>(){{ add(mockTrack); }});
        when(spySetOfTracks.setIsEmpty()).thenReturn(false);
        when(mockTrack.isDeleteStatus()).thenReturn(false);
        when(mockTrack.getDuration()).thenReturn("01:02:03");
        spySetOfTracks.printDurationForAllTracks();
        assertEquals("Duration of all tracks together: 1:2:3\r\n", outContent.toString());
    }
    @Test
    void testPrintDurationForAllTracksTwoTrackExist() {
        MusicTrack newMockTrack = mock(MusicTrack.class);
        ReflectionTestUtils.setField(spySetOfTracks, "trackList",
                new ArrayList<>(){{ add(mockTrack); add(newMockTrack); }});
        when(spySetOfTracks.setIsEmpty()).thenReturn(false);
        when(mockTrack.isDeleteStatus()).thenReturn(false);
        when(mockTrack.getDuration()).thenReturn("01:02:03");
        when(newMockTrack.getDuration()).thenReturn("00:58:57");
        spySetOfTracks.printDurationForAllTracks();
        assertEquals("Duration of all tracks together: 2:1:0\r\n", outContent.toString());
    }

    @Test
    void testSetIsEmptyTrue(){
        when(mockTrackList.isEmpty()).thenReturn(true);
        assertTrue(spySetOfTracks.setIsEmpty());
    }

    @Test
    void testSetIsEmptyFalse(){
        when(mockTrackList.isEmpty()).thenReturn(false);
        assertFalse(spySetOfTracks.setIsEmpty());
    }

    @Test
    void testMakeMapForParams(){
        Map<String, String> result = spySetOfTracks.makeMapForParams(new String[]{"add", "track",
                "name=testName", "duration=00:00:01"});
        Map<String, String> expected = new HashMap<>(){{ put("name","testName"); put("duration", "00:00:01");}};
        assertEquals(expected, result);
    }

    @Test
    void testGetTrackList(){
        assertEquals(mockTrackList, spySetOfTracks.getTrackList());
    }
}