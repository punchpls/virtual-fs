package edu.gavrilov.fs.command.fs;

import edu.gavrilov.fs.command.Command;

// Этот интерфейс не обязателен, т.к. все команды могут напрямую имплементить Command
// но в больших проектах подобный интерфейс может служить по сути логической группировкой
// для разных групп команд
public interface FileSystemCommand extends Command {
}
