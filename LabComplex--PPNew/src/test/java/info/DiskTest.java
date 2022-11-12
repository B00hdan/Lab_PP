package info;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.test.util.ReflectionTestUtils;
import track.Album;
import track.MusicTrack;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DiskTest {
    @Mock
    SetOfTracks mockSet;

    @Mock
    MusicTrack mockTrack;

    @Mock
    Album mockAlbum;

    @Spy
    @InjectMocks
    Disk spyDisk;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.initMocks(this);
        ReflectionTestUtils.setField(spyDisk, "set", mockSet);
    }

    /*@Test
    void testConnectDiskIfOldFile() {
        given(spyDisk.checkIfFileAlreadyExist()).getMock();//willReturn(false);
        doNothing().when(spyDisk).scanExistData();
        try {
            spyDisk.connectDisk("", 0);
        } catch(Exception ignored) {
        }
        verify(spyDisk, times(1)).scanExistData();
    } */

    /*@Test
    void testConnectDiskIfNewFile() { //!!!
        when(spyDisk.checkIfFileAlreadyExist()).thenReturn(true);
        doNothing().when(spyDisk).scanExistData();
        try {
            spyDisk.connectDisk("", 0);
        } catch(Exception ignored) {}
        verify(spyDisk, times(0)).scanExistData();
    }*/

    @Test
    void testDisconnectDisk() {
        doNothing().when(spyDisk).writeToFile();
        spyDisk.disconnectDisk();
        verify(spyDisk, times(1)).writeToFile();
    }

    @Test
    void testConnectionStatusTrue() {
        assertTrue(spyDisk.connectionStatus());
    }

    @Test
    void testConnectionStatusFalse() {
        ReflectionTestUtils.setField(spyDisk, "set", null);
        assertFalse(spyDisk.connectionStatus());
    }

    @Test
    void testAddAlbumOnDiskIncorrectParameters() {
        assertFalse(spyDisk.addAlbumOnDisk(new String[]{"add", "album"}));
    }

    @Test
    void testAddAlbumOnDiskNotEnoughSpace() throws IOException {
        ReflectionTestUtils.setField(spyDisk, "size", 1);
        File testFile = new File("test.txt");
        FileWriter tempWriter = new FileWriter("test.txt");
        tempWriter.write("name=testName duration=00:00:01 genre=Pop\n name=testName2 duration=00:00:01 genre=Pop");
        tempWriter.close();
        ReflectionTestUtils.setField(spyDisk, "size", 1);
        doNothing().when(mockSet).addNewAlbum(any());
        when(spyDisk.deleteAlbumFromDisk("name")).thenReturn(true);
        assertFalse(spyDisk.addAlbumOnDisk(new String[]{"add", "album", "testAlbum", "test.txt"}));
        testFile.deleteOnExit();
    }

    @Test
    void testAddAlbumOnDiskZeroSpace() {
        ReflectionTestUtils.setField(spyDisk, "size", 0);
        assertFalse(spyDisk.addAlbumOnDisk(new String[]{"add", "album", "name", "way"}));
    }

    @Test
    void testAddAlbumOnDiskAddNewTracks() throws IOException {
        File testFile = new File("test.txt");
        FileWriter tempWriter = new FileWriter("test.txt");
        tempWriter.write("name=testName duration=00:00:01 genre=Pop");
        tempWriter.close();
        ReflectionTestUtils.setField(spyDisk, "size", 1);
        doNothing().when(mockSet).addNewAlbum(any());
        when(mockAlbum.getNumbOfTracks()).thenReturn(1);
        assertTrue(spyDisk.addAlbumOnDisk(new String[]{"add", "album", "testAlbum", "test.txt"}));
        verify(spyDisk, times(0)).deleteAlbumFromDisk("testAlbum");
        testFile.deleteOnExit();
    }

    @Test
    void testAddNewTrackOnDiskZeroSpace() {
        ReflectionTestUtils.setField(spyDisk, "size", 0);
        assertFalse(spyDisk.addNewTrackOnDisk(new String[]{"add", "track"}));
    }

    @Test
    void testAddNewTrackOnDisCorrectTrack() {
        String[] testParams = new String[]{"add", "track"};
        ReflectionTestUtils.setField(spyDisk, "size", 1);
        when(mockSet.addNewTrack(testParams)).thenReturn(new MusicTrack());
        assertTrue(spyDisk.addNewTrackOnDisk(testParams));
    }

    @Test
    void testAddNewTrackOnDisNotCorrectTrack() {
        String[] testParams = new String[]{"add", "track"};
        ReflectionTestUtils.setField(spyDisk, "size", 1);
        when(mockSet.addNewTrack(testParams)).thenReturn(null);
        assertFalse(spyDisk.addNewTrackOnDisk(testParams));
    }

    @Test
    void testDeleteTrackFromDiskTrackExist() {
        when(mockSet.deleteTrackByName("name")).thenReturn(true);
        assertTrue(spyDisk.deleteTrackFromDisk("name"));
        verify(mockSet, times(1)).deleteTrackByName("name");
    }

    @Test
    void testDeleteTrackFromDiskTrackNotExist() {
        when(mockSet.deleteTrackByName("name")).thenReturn(false);
        assertFalse(spyDisk.deleteTrackFromDisk("name"));
        verify(mockSet, times(1)).deleteTrackByName("name");
    }

    @Test
    void deleteAlbumFromDiskAlbumExist() {
        ReflectionTestUtils.setField(spyDisk, "size", 0);
        when(mockSet.deleteTracksByAlbumName("name")).thenReturn(1);
        assertTrue(spyDisk.deleteAlbumFromDisk("name"));
        verify(mockSet, times(1)).deleteTracksByAlbumName("name");
    }

    @Test
    void deleteAlbumFromDiskAlbumNotExist() {
        ReflectionTestUtils.setField(spyDisk, "size", 0);
        when(mockSet.deleteTracksByAlbumName("name")).thenReturn(0);
        assertFalse(spyDisk.deleteAlbumFromDisk("name"));
        verify(mockSet, times(1)).deleteTracksByAlbumName("name");
    }

    @Test
    void testRestoreTrackLastOnDisk() {
        doNothing().when(mockSet).restoreLastTracks();
        spyDisk.restoreTrackLastOnDisk();
        verify(mockSet, times(1)).restoreLastTracks();
    }

    @Test
    void testChangeTrackParamsOnDisk() {
        when(mockSet.changeTrackParams(any())).thenReturn(true);
        assertTrue(spyDisk.changeTrackParamsOnDisk(new String[]{}));
    }

    @Test
    void testReturnParamsForLastModified() {
        doNothing().when(mockSet).restorePreviousParamsForTrack();
        spyDisk.returnParamsForLastModified();
        verify(mockSet, times(1)).restorePreviousParamsForTrack();
    }

    @Test
    void testPrintFromDiskAllByIncorrectParameters() {
        assertFalse(spyDisk.printFromDiskAllBy(new String[]{"find"}));
    }

    @Test
    void testPrintFromDiskAllByDuration() {
        when(mockSet.printTracksByDurationBorders(new String[]{"find", "duration"})).thenReturn(true);
        assertTrue(spyDisk.printFromDiskAllBy(new String[]{"find", "duration"}));
        verify(mockSet).printTracksByDurationBorders(new String[]{"find", "duration"});
    }

    @Test
    void testPrintFromDiskAll() {
        when(mockSet.getTrackList()).thenReturn(Collections.singletonList(mockTrack));
        when(mockTrack.isDeleteStatus()).thenReturn(false);
        assertTrue(spyDisk.printFromDiskAllBy(new String[]{"find", "all"}));
    }

    @Test
    void testPrintFromDiskAllByParam() {
        when(mockSet.printTracksBy(new String[]{"name", "test"}))
                .thenReturn(true);
        assertTrue(spyDisk.printFromDiskAllBy(new String[]{"find", "name=test"}));
    }

    @Test
    void testSortOnDiskBy() {
        spyDisk.sortOnDiskBy("name");
        verify(mockSet).sortListBy("name");
    }

    @Test
    void calculateDurationOfAllTracks() {
        spyDisk.calculateDurationOfAllTracks();
        verify(mockSet).printDurationForAllTracks();
    }
}